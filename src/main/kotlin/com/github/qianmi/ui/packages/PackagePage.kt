package com.github.qianmi.ui.packages

import cn.hutool.core.thread.ThreadUtil
import cn.hutool.core.util.NumberUtil
import com.github.qianmi.action.CopyAction
import com.github.qianmi.action.link.BugattiAction
import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.domain.project.link.GitlabLink
import com.github.qianmi.infrastructure.domain.vo.BugattiCIBuildResult
import com.github.qianmi.infrastructure.domain.vo.BugattiLastVersionResult
import com.github.qianmi.infrastructure.util.BugattiHttpUtil
import com.github.qianmi.infrastructure.util.GitUtil
import com.github.qianmi.infrastructure.util.NotifyUtil
import com.github.qianmi.ui.MyJDialog
import com.github.qianmi.ui.publish.PublishPage
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import java.util.stream.Collectors
import javax.swing.*

class PackagePage(override var project: Project) : MyJDialog() {
    //根容器
    lateinit var contentPanel: JPanel

    private var isPackageResult: Boolean? = null
    private var packageVersion: String = ""

    //分支信息
    private lateinit var mapBetaPreBranch: Map<String, BugattiLastVersionResult>
    private lateinit var releasePreBranch: BugattiLastVersionResult

    //tab容器
    private lateinit var tabPanel: JTabbedPane
    private lateinit var snapshotTabPanel: JPanel
    private lateinit var betaTabPanel: JPanel
    private lateinit var releaseTabPanel: JPanel


    /* snapshot */
    private lateinit var snapshotContent: JPanel

    //snapshot-项目名
    private lateinit var snapshotJenkinsJLabel: JLabel
    private lateinit var snapshotJenkinsText: JTextField

    //snapshot-git地址
    private lateinit var snapshotGitUrlJLabel: JLabel
    private lateinit var snapshotGitUrlText: JTextField

    //snapshot-git分支
    private lateinit var snapshotGitBranchJLabel: JLabel
    private lateinit var snapshotGitBranchSelected: JComboBox<String>

    /* beta */
    private lateinit var betaContent: JPanel

    //beta-项目名
    private lateinit var betaJenkinsJLabel: JLabel
    private lateinit var betaJenkinsText: JTextField

    //beta-git地址
    private lateinit var betaGitUrlJLabel: JLabel
    private lateinit var betaGitUrlText: JTextField

    //beta-git分支
    private lateinit var betaGitBranchLabel: JLabel
    private lateinit var betaGitBranchSelected: JComboBox<String>

    //beta-发布版本号
    private lateinit var betaVersionJLabel: JLabel
    private lateinit var betaVersionText: JTextField

    //beta-开发版本号
    private lateinit var betaSnapshotVersionJLabel: JLabel
    private lateinit var betaSnapshotVersionText: JTextField

    /* release */
    private lateinit var releaseContent: JPanel

    //release-项目名
    private lateinit var releaseJenkinsJLabel: JLabel
    private lateinit var releaseJenkinsText: JTextField

    //release-git地址
    private lateinit var releaseGitUrlJLabel: JLabel
    private lateinit var releaseGitUrlText: JTextField

    //release-git分支
    private lateinit var releaseGitBranchLabel: JLabel
    private lateinit var releaseGitBranchSelected: JComboBox<String>

    //beta-发布版本号
    private lateinit var releaseVersionJLabel: JLabel
    private lateinit var releaseVersionText: JTextField

    //beta-开发版本号
    private lateinit var releaseSnapshotVersionJLabel: JLabel
    private lateinit var releaseSnapshotVersionText: JTextField

    //构建按钮
    private lateinit var buildButton: JButton

    //构建且发布按钮
    private lateinit var buildAndPublishButton: JButton

    init {
        //初始化tab
        this.initSnapshot()
        this.initBeta()
        this.initRelease()
        //分支处理
        this.initBranchHandler()
        //构建打包按钮
        this.initBuildButton()
        //构建打包且发布按钮
        this.initBuildAndPublishButton()
    }

    override fun getRootContainer(): JPanel {
        return this.contentPanel
    }

    override fun getTitle(): String {
        return "打包"
    }

    private fun initSnapshot() {
        //项目名
        this.snapshotJenkinsText.text = this.getMyProject().projectInfo.jenkins
        //git地址
        this.snapshotGitUrlText.text = GitlabLink.getInstance().getBrowserUrl(this.project)
    }


    private fun initBeta() {
        //项目名
        this.betaJenkinsText.text = this.getMyProject().projectInfo.projectName
        //git地址
        this.betaGitUrlText.text = this.getMyProject().projectInfo.git
        //beta分支
        this.mapBetaPreBranch = BugattiHttpUtil.mapLastBetaVersion(this.getMyProject())
        //版本
        updateBetaVersionInfo(GitUtil.getCurrentBranchName(project))
    }


    private fun initRelease() {
        //项目名
        this.releaseJenkinsText.text = this.getMyProject().projectInfo.projectName
        //git地址
        this.releaseGitUrlText.text = this.getMyProject().projectInfo.git

        //release版本
        this.releasePreBranch = BugattiHttpUtil.getLastReleaseVersion(this.getMyProject())!!

        //默认版本信息
        this.releaseVersionText.text = this.releasePreBranch.version
        this.releaseSnapshotVersionText.text = this.releasePreBranch.sVersion
    }


    private fun buildButtonListener(
        comBox: JComboBox<String>, buildType: BuildType,
        version: String, snapshotVersion: String,
    ) {
        //jenkins构建
        val branchName = comBox.selectedItem!!.toString()

        val ciResult = when (buildType) {
            BuildType.SNAPSHOT -> BugattiHttpUtil.jenkinsCIBuild(this.getMyProject(), branchName)
            BuildType.BETA, BuildType.RELEASE -> BugattiHttpUtil.jenkinsCIRelease(this.getMyProject(),
                branchName,
                version,
                snapshotVersion)
        }
        //success
        if (ciResult.success) {
            this.goPackage(buildType, version, branchName)
        } else {
            val message = String.format("提交失败！ %s", ciResult.errMsg)
            NotifyUtil.notifyInfoWithAction(project, message, BugattiAction.defaultAction())
        }
        isVisible = false
    }

    /**
     * 打包进度条
     */
    private fun goPackage(buildType: BuildType, version: String, branchName: String) {
        val myProject = this.getMyProject()

        object : Task.Backgroundable(this.project, "Package: 正在打包，请稍后", false, DEAF) {
            override fun run(indicator: ProgressIndicator) {

                for (i in 1..999) {

                    indicator.checkCanceled()
                    indicator.text = "打包已消耗 $i 秒"
                    indicator.text2 = version
                    ThreadUtil.sleep(1000L)
                    //前120秒展示进度条
                    if (i <= 120) {
                        indicator.isIndeterminate = false
                        indicator.fraction = NumberUtil.div(i, 120, 2).toDouble()
                    } else {
                        indicator.isIndeterminate = true
                    }

                    //每3秒查询一次打包结果
                    if (i % 3 != 0) {
                        continue
                    }

                    //打包
                    val ciBuildResult = when (buildType) {
                        BuildType.SNAPSHOT -> {
                            BugattiHttpUtil.ciBuildResult(myProject)
                        }
                        BuildType.BETA, BuildType.RELEASE -> {
                            BugattiHttpUtil.ciReleaseResult(myProject, version, branchName)
                        }
                    }

                    if (ciBuildResult.running()) {
                        continue
                    }
                    //回调通知
                    goPackageResultNotify(ciBuildResult)
                    break
                }
            }
        }.queue()
    }


    private fun goPackageResultNotify(ciBuildResult: BugattiCIBuildResult) {
        val myProject = IdeaProject.getInstance(project)
        if (ciBuildResult.isSuccess()) {
            this.isPackageResult = true
            this.packageVersion = ciBuildResult.version
            //idea 通知
            NotifyUtil.notifyInfoWithAction(project,
                "构建成功啦，当前版本号：${ciBuildResult.version}",
                listOf(BugattiAction.defaultAction(), CopyAction.instanceOf("复制版本信息",
                    "以下项目有最新版本了：" +
                            "\n----------------------------------------" +
                            "\n\t项目名：${myProject.projectInfo.projectName}" +
                            "\n\t版本号：${ciBuildResult.version}" +
                            "\n----------------------------------------" +
                            "\n变更内容：" +
                            "\n1. ")))
        } else {
            this.isPackageResult = false
            //idea 通知
            NotifyUtil.notifyInfoWithAction(project, "构建失败，出了点问题~", BugattiAction.defaultAction())
        }

    }

    private fun initBuildButton() {
        //构建按钮 snapshot
        this.buildButton.addActionListener {
            when (this.tabPanel.selectedIndex) {
                //snapshot
                0 -> {
                    buildButtonListener(this.snapshotGitBranchSelected, BuildType.SNAPSHOT, "", "")
                }
                //beta
                1 -> {
                    buildButtonListener(this.betaGitBranchSelected,
                        BuildType.BETA,
                        this.betaVersionText.text,
                        this.betaSnapshotVersionText.text)
                }
                //release
                2 -> {
                    buildButtonListener(this.releaseGitBranchSelected,
                        BuildType.BETA,
                        this.releaseVersionText.text,
                        this.releaseSnapshotVersionText.text)
                }
            }
        }
    }

    private fun initBuildAndPublishButton() {
        //构建按钮 snapshot
        this.buildAndPublishButton.addActionListener {
            val publishPage = PublishPage(this.project)
            publishPage.handlerPackageSource()
            publishPage.open()
            this.buildButton.doClick()

            Thread {
                ThreadUtil.sleep(20_000L)
                while (this.isPackageResult == null) {
                    ThreadUtil.sleep(1_000L)
                }
                if (this.isPackageResult == true) {
                    val version =
                        BugattiHttpUtil.getVersionList(this.getMyProject()).first { it.version == packageVersion }
                    publishPage.publish(version.id)
                }
            }.start()
        }
    }

    private fun initBranchHandler() {
        //分支列表
        val branchList = BugattiHttpUtil.getBranchList(this.project).stream().map { branch -> branch.name }
            .collect(Collectors.toList())
        //填充分支
        branchList.forEach { branchName ->
            this.snapshotGitBranchSelected.addItem(branchName)
            this.betaGitBranchSelected.addItem(branchName)
            this.releaseGitBranchSelected.addItem(branchName)
        }
        //当前分支
        val currentBranch = GitUtil.getCurrentBranchName(project)

        //beta分支选择器
        this.betaGitBranchSelected.addItemListener {
            updateBetaVersionInfo(this.betaGitBranchSelected.selectedItem as String)
        }

        //默认选中分支
        if (branchList.contains(currentBranch)) {
            this.snapshotGitBranchSelected.selectedItem = currentBranch
            this.betaGitBranchSelected.selectedItem = currentBranch
            this.releaseGitBranchSelected.selectedItem = currentBranch
        }
    }

    /**
     * 更新beta的版本信息
     */
    private fun updateBetaVersionInfo(versionName: String) {
        //默认版本信息
        val versionInfo = this.mapBetaPreBranch[versionName]
        if (versionInfo !== null) {
            this.betaVersionText.text = versionInfo.version
            this.betaSnapshotVersionText.text = versionInfo.sVersion
        } else {
            this.betaVersionText.text = ""
            this.betaSnapshotVersionText.text = ""
        }
    }

}