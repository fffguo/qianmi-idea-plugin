package com.github.qianmi.ui

import com.github.qianmi.action.BugattiAction
import com.github.qianmi.action.packaging.PackageNotify
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.services.vo.BugattiLastVersionResult
import com.github.qianmi.ui.PackagePage.BuildType.*
import com.github.qianmi.util.BugattiHttpUtil
import com.github.qianmi.util.GitUtil
import com.github.qianmi.util.NotifyUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import java.awt.event.KeyEvent
import java.util.stream.Collectors
import javax.swing.*

class PackagePage : JDialog {
    private var project: Project? = null
    private var myProject: AllProject.MyProject? = null

    //分支信息
    private var mapBetaPreBranch: Map<String, BugattiLastVersionResult>? = null
    private var releasePreBranch: BugattiLastVersionResult? = null

    //默认文案
    private val buildSuccessMsg = "提交成功！jenkins 正在加急构建中  ヽ(￣д￣;)ノ"
    private val buildGoBugattiMsg = "Go Bugatti Look Look"
    private val buildFailMsg = "提交失败！ %s"

    //根容器
    private var contentPanel: JPanel? = null

    //tab容器
    private var tabPanel: JTabbedPane? = null
    private var snapshotTabPanel: JPanel? = null
    private var betaTabPanel: JPanel? = null
    private var releaseTabPanel: JPanel? = null


    /* snapshot */
    private var snapshotContent: JPanel? = null

    //snapshot-项目名
    private var snapshotJenkinsJLabel: JLabel? = null
    private var snapshotJenkinsText: JTextField? = null

    //snapshot-git地址
    private var snapshotGitUrlJLabel: JLabel? = null
    private var snapshotGitUrlText: JTextField? = null

    //snapshot-git分支
    private var snapshotGitBranchJLabel: JLabel? = null
    private var snapshotGitBranchSelected: JComboBox<String>? = null

    //snapshot-构建按钮
    private var snapshotBuildButton: JButton? = null


    /* beta */
    private var betaContent: JPanel? = null

    //beta-项目名
    private var betaJenkinsJLabel: JLabel? = null
    private var betaJenkinsText: JTextField? = null

    //beta-git地址
    private var betaGitUrlJLabel: JLabel? = null
    private var betaGitUrlText: JTextField? = null

    //beta-git分支
    private var betaGitBranchLabel: JLabel? = null
    private var betaGitBranchSelected: JComboBox<String>? = null

    //beta-发布版本号
    private var betaVersionJLabel: JLabel? = null
    private var betaVersionText: JTextField? = null

    //beta-开发版本号
    private var betaSnapshotVersionJLabel: JLabel? = null
    private var betaSnapshotVersionText: JTextField? = null

    //beta-构建按钮
    private var betaBuildButton: JButton? = null


    /* release */
    private var releaseContent: JPanel? = null

    //release-项目名
    private var releaseJenkinsJLabel: JLabel? = null
    private var releaseJenkinsText: JTextField? = null

    //release-git地址
    private var releaseGitUrlJLabel: JLabel? = null
    private var releaseGitUrlText: JTextField? = null

    //release-git分支
    private var releaseGitBranchLabel: JLabel? = null
    private var releaseGitBranchSelected: JComboBox<String>? = null

    //beta-发布版本号
    private var releaseVersionJLabel: JLabel? = null
    private var releaseVersionText: JTextField? = null

    //beta-开发版本号
    private var releaseSnapshotVersionJLabel: JLabel? = null
    private var releaseSnapshotVersionText: JTextField? = null

    //release-构建按钮
    private var releaseBuildButton: JButton? = null


    constructor(project: Project) {
        contentPane = this.contentPanel
        modalityType = ModalityType.APPLICATION_MODAL
        this.project = project
        this.myProject = AllProject.currentProject(project)
        init()
    }

    private fun initSnapshot() {
        //项目名
        this.snapshotJenkinsText!!.text = myProject!!.jenkins.projectName
        //git地址
        this.snapshotGitUrlText!!.text = myProject!!.gitlab.url
    }


    private fun initBeta() {
        //项目名
        this.betaJenkinsText!!.text = this.myProject!!.jenkins.projectName
        //git地址
        this.betaGitUrlText!!.text = this.myProject!!.gitlab.url
    }


    private fun initRelease() {
        //项目名
        this.releaseJenkinsText!!.text = this.myProject!!.jenkins.projectName
        //git地址
        this.releaseGitUrlText!!.text = this.myProject!!.gitlab.url
        //默认版本信息
        if (this.releasePreBranch == null) {
            this.releasePreBranch = BugattiHttpUtil.getLastReleaseVersion(this.myProject!!)
        }
        this.releaseVersionText!!.text = this.releasePreBranch!!.version
        this.releaseSnapshotVersionText!!.text = this.releasePreBranch!!.sVersion

    }


    private fun buildButtonListener(
        selected: JComboBox<String>?,
        buildType: BuildType,
        version: String,
        snapshotVersion: String,
    ) {

        //jenkins构建
        val branchName = selected!!.selectedItem!!.toString()

        val ciResult = when (buildType) {
            SNAPSHOT -> BugattiHttpUtil.jenkinsCIBuild(this.myProject!!, branchName)
            BETA, RELEASE -> BugattiHttpUtil.jenkinsCIRelease(this.myProject!!, branchName, version, snapshotVersion)
        }

        val bugattiAction = BugattiAction()
        bugattiAction.templatePresentation.text = this.buildGoBugattiMsg

        var message = String.format(this.buildFailMsg, ciResult.errMsg)

        //success
        if (ciResult.success) {
            message = this.buildSuccessMsg
            //回调
            PackageNotify(this.project!!, buildType, version, branchName)
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

    private fun init() {
        //初始化snapshot
        this.initSnapshot()
        //tab点击事件
        this.initTabSwitch()
        //分支处理
        this.initBranchHandler()
        //构建打包按钮
        this.initBuildButton()
        //初始化esc退出事件
        this.initEscEvent()
    }

    private fun initBuildButton() {
        //构建按钮 snapshot
        this.snapshotBuildButton!!.addActionListener {
            buildButtonListener(this.snapshotGitBranchSelected, SNAPSHOT, "", "")
        }
        //构建按钮 beta
        this.betaBuildButton!!.addActionListener {
            buildButtonListener(this.betaGitBranchSelected,
                BETA,
                this.betaVersionText!!.text,
                this.betaSnapshotVersionText!!.text)
        }
        //构建按钮 release
        this.releaseBuildButton!!.addActionListener {
            buildButtonListener(this.releaseGitBranchSelected,
                BETA,
                this.releaseVersionText!!.text,
                this.releaseSnapshotVersionText!!.text)
        }
    }

    /**
     * esc键关闭窗口
     */
    private fun initEscEvent() {
        contentPanel!!.registerKeyboardAction({ this.dispose() },
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
    }

    private fun initTabSwitch() {
        tabPanel!!.addChangeListener {
            when ((it.source as JTabbedPane).selectedIndex) {
                0 -> initSnapshot()
                1 -> initBeta()
                2 -> initRelease()
            }
        }
    }

    private fun initBranchHandler() {
        //分支列表
        val branchList = BugattiHttpUtil.getBranchList(this.myProject!!).stream().map { branch -> branch.name }
            .collect(Collectors.toList())
        //填充分支
        branchList.forEach { branchName ->
            this.snapshotGitBranchSelected!!.addItem(branchName)
            this.betaGitBranchSelected!!.addItem(branchName)
            this.releaseGitBranchSelected!!.addItem(branchName)
        }
        //当前分支
        val currentBranch = GitUtil.getCurrentBranchName(project!!)

        //beta分支选择器
        this.betaGitBranchSelected!!.addItemListener {
            updateBetaVersionInfo(this.betaGitBranchSelected!!.selectedItem as String)
        }

        //默认选中分支
        if (branchList.contains(currentBranch)) {
            this.snapshotGitBranchSelected!!.selectedItem = currentBranch
            this.betaGitBranchSelected!!.selectedItem = currentBranch
            this.releaseGitBranchSelected!!.selectedItem = currentBranch
        }
    }

    /**
     * 更新beta的版本信息
     */
    private fun updateBetaVersionInfo(versionName: String) {
        //默认版本信息
        if (this.mapBetaPreBranch.isNullOrEmpty()) {
            this.mapBetaPreBranch = BugattiHttpUtil.mapLastBetaVersion(this.myProject!!)
        }
        val versionInfo = this.mapBetaPreBranch!![versionName]
        if (versionInfo !== null) {
            this.betaVersionText!!.text = versionInfo.version
            this.betaSnapshotVersionText!!.text = versionInfo.sVersion
        } else {
            this.betaVersionText!!.text = ""
            this.betaSnapshotVersionText!!.text = ""
        }
    }

    enum class BuildType {
        SNAPSHOT, BETA, RELEASE
    }

}