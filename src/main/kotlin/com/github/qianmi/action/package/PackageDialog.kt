package com.github.qianmi.action.`package`

import com.github.qianmi.action.BugattiAction
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.util.BugattiHttpUtil
import com.github.qianmi.util.BugattiHttpUtil.jenkinsCIBuild
import com.github.qianmi.util.NotifyUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.vcs.changes.ui.CurrentBranchComponent
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.util.ui.components.BorderLayoutPanel
import java.awt.Color
import java.util.stream.Collectors
import javax.swing.*

/**
 * @author lfg
 * @version 1.0
 */
class PackageDialog(private val project: Project) : DialogWrapper(project) {
    //总容器
    private var panel = JPanel(VerticalLayout(20, SwingConstants.LEFT))

    //当前项目信息
    private var myProject = AllProject.currentProject(project)

    //错误提示
    private var errors: Array<ValidationInfo> = arrayOf()

    //选择框
    private var comboBox = ComboBox(arrayOf(""))

    init {
        errors = arrayOf(
//            ValidationInfo("editableComboBox: should contain some value", myPanel?.comboBox),
//            ValidationInfo("Spinner should contain values between 20 and 30", myPanel?.spinner)
        )
        init()
//    myPanel?.field5.addActionListener(ActionListener { e: ActionEvent? -> myPanel.spinner.setEnabled(myPanel.field5.isSelected()) })
//    myPanel.spinner.setEnabled(myPanel.field5.isSelected())
    }

    override fun doOKAction() {
        super.doOKAction()
        //jenkins构建
        val ciResult = jenkinsCIBuild(myProject, comboBox.selectedItem!!.toString())

        val bugattiAction = BugattiAction()
        bugattiAction.templatePresentation.text = "Go Bugatti Look Look"

        var message = "提交成功！jenkins 正在加急构建中  ヽ(￣д￣;)ノ"
        if (!ciResult.success) {
            message = String.format("提交失败！%s  %S", ciResult.errMsg, "╥╯^╰╥")
        }
        //idea 通知
        NotifyUtil.notifyInfoWithAction(project, message, bugattiAction)

        //构建结果通知
        PackageNotify(project)
    }

    /**
     * 处理验证
     */
    override fun doValidateAll(): List<ValidationInfo> {
        return ArrayList()
    }

    /**
     * 创建容器
     */
    override fun createCenterPanel(): JComponent {

        //jenkins项目名
        panel.add(buildJenkinsProjectName())
        //git地址
        panel.add(buildGitAddress())
        //git分支
        panel.add(buildGitBranch())
        (this.okAction as DialogWrapper.OkAction).putValue("Name", "构建")

        return panel
    }

    private fun buildJenkinsProjectName(): JPanel {
        val jTextArea = JTextArea(myProject.jenkins.projectName)
        jTextArea.isEditable = false
        jTextArea.background = Color(0, 0, 0, 0)

        val label = JLabel("Jenkins：")
        label.labelFor = jTextArea

        val jPanel = BorderLayoutPanel(5, 5)
        jPanel.addToLeft(label)
        jPanel.addToRight(jTextArea)
        return jPanel
    }

    private fun buildGitAddress(): JPanel {
        val jTextArea = JTextArea(myProject.gitlab.url)
        jTextArea.isEditable = false
        jTextArea.background = Color(0, 0, 0, 0)

        val label = JLabel("Git地址：")
        label.labelFor = jTextArea

        val jPanel = BorderLayoutPanel(5, 5)
        jPanel.addToLeft(label)
        jPanel.addToRight(jTextArea)
        return jPanel
    }

    private fun buildGitBranch(): JPanel {
        val branchList = BugattiHttpUtil.getBranchList(myProject)
            .stream()
            .map { branch -> branch.name }
            .collect(Collectors.toList())

        comboBox = ComboBox(branchList.toTypedArray())

        val currentBranch = CurrentBranchComponent.getCurrentBranch(project, project.projectFile!!)

        if (currentBranch != null && branchList.contains(currentBranch.branchName ?: "")) {
            comboBox.item = currentBranch.branchName
        }
        val label = JLabel("Git分支：")
        label.labelFor = comboBox

        val jPanel = BorderLayoutPanel(5, 5)
        jPanel.addToLeft(label)
        jPanel.addToRight(comboBox)
        return jPanel
    }

}