package com.github.qianmi.ui

import com.github.qianmi.action.link.BugattiAction
import com.github.qianmi.action.packaging.PackageNotify
import com.github.qianmi.infrastructure.domain.project.AllProject
import com.github.qianmi.infrastructure.util.BugattiHttpUtil
import com.github.qianmi.infrastructure.util.GitUtil
import com.github.qianmi.infrastructure.util.NotifyUtil
import com.github.qianmi.services.vo.BugattiLastVersionResult
import com.github.qianmi.ui.PackagePage.BuildType.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import java.awt.event.KeyEvent
import java.util.stream.Collectors
import javax.swing.*

class PackagePage(private var project: Project) : JDialog() {
    private var myProject: com.github.qianmi.infrastructure.domain.project.AllProject.MyProject

    //分支信息
    private lateinit var mapBetaPreBranch: Map<String, BugattiLastVersionResult>
    private lateinit var releasePreBranch: BugattiLastVersionResult

    //默认文案
    private val buildSuccessMsg = "提交成功！jenkins 正在加急构建中  ヽ(￣д￣;)ノ"
    private val buildGoBugattiMsg = "Go Bugatti Look Look"
    private val buildFailMsg = "提交失败！ %s"

    //根容器
    private lateinit var contentPanel: JPanel

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

    //snapshot-构建按钮
    private lateinit var snapshotBuildButton: JButton


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

    //beta-构建按钮
    private lateinit var betaBuildButton: JButton


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

    //release-构建按钮
    private lateinit var releaseBuildButton: JButton

    init {
        contentPane = this.contentPanel
        modalityType = ModalityType.APPLICATION_MODAL
        this.myProject = AllProject.currentProject(this.project)
        //分支处理
        this.initBranchHandler()
        //初始化tab
        this.initSnapshot()
        this.initBeta()
        this.initRelease()
        //构建打包按钮
        this.initBuildButton()
        //初始化esc退出事件
        this.initEscEvent()
    }

    private fun initSnapshot() {
        //项目名
        this.snapshotJenkinsText.text = myProject.jenkins.projectName
        //git地址
        this.snapshotGitUrlText.text = myProject.gitlab.url
    }


    private fun initBeta() {
        //项目名
        this.betaJenkinsText.text = this.myProject.jenkins.projectName
        //git地址
        this.betaGitUrlText.text = this.myProject.gitlab.url
        //beta分支
        this.mapBetaPreBranch = BugattiHttpUtil.mapLastBetaVersion(this.myProject)
        //版本
        updateBetaVersionInfo(GitUtil.getCurrentBranchName(project))
    }


    private fun initRelease() {
        //项目名
        this.releaseJenkinsText.text = this.myProject.jenkins.projectName
        //git地址
        this.releaseGitUrlText.text = this.myProject.gitlab.url

        //release版本
        this.releasePreBranch = BugattiHttpUtil.getLastReleaseVersion(this.myProject)!!

        //默认版本信息
        this.releaseVersionText.text = this.releasePreBranch.version
        this.releaseSnapshotVersionText.text = this.releasePreBranch.sVersion

    }


    private fun buildButtonListener(
        selected: JComboBox<String>,
        buildType: BuildType,
        version: String,
        snapshotVersion: String,
    ) {

        //jenkins构建
        val branchName = selected.selectedItem!!.toString()

        val ciResult = when (buildType) {
            SNAPSHOT -> BugattiHttpUtil.jenkinsCIBuild(this.myProject, branchName)
            BETA, RELEASE -> BugattiHttpUtil.jenkinsCIRelease(this.myProject, branchName, version, snapshotVersion)
        }

        val bugattiAction = BugattiAction()
        bugattiAction.templatePresentation.text = this.buildGoBugattiMsg

        var message = String.format(this.buildFailMsg, ciResult.errMsg)

        //success
        if (ciResult.success) {
            message = this.buildSuccessMsg
            //回调
            PackageNotify(this.project, buildType, version, branchName)
        }
        //idea 通知
        NotifyUtil.notifyInfoWithAction(project, message, bugattiAction)
        isVisible = false
    }


    /**
     * 打开窗口
     */
    fun open() {
        title = "打包"
        pack()
        //两个屏幕处理出现问题，跳到主屏幕去了
        setLocationRelativeTo(WindowManager.getInstance().getFrame(project))
        isVisible = true
    }

    private fun initBuildButton() {
        //构建按钮 snapshot
        this.snapshotBuildButton.addActionListener {
            buildButtonListener(this.snapshotGitBranchSelected, SNAPSHOT, "", "")
        }
        //构建按钮 beta
        this.betaBuildButton.addActionListener {
            buildButtonListener(this.betaGitBranchSelected,
                BETA,
                this.betaVersionText.text,
                this.betaSnapshotVersionText.text)
        }
        //构建按钮 release
        this.releaseBuildButton.addActionListener {
            buildButtonListener(this.releaseGitBranchSelected,
                BETA,
                this.releaseVersionText.text,
                this.releaseSnapshotVersionText.text)
        }
    }

    /**
     * esc键关闭窗口
     */
    private fun initEscEvent() {
        contentPanel.registerKeyboardAction({ this.dispose() },
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
    }

    private fun initBranchHandler() {
        //分支列表
        val branchList = BugattiHttpUtil.getBranchList(this.myProject).stream().map { branch -> branch.name }
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

    enum class BuildType {
        SNAPSHOT, BETA, RELEASE
    }

}