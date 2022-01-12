package com.github.qianmi.action.dubboinvoke

import com.github.qianmi.infrastructure.domain.project.tools.DubboAdminInvoke
import com.github.qianmi.infrastructure.extend.JsonExtend.isJsonString
import com.github.qianmi.infrastructure.util.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiParameter
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.SideBorder
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.util.ui.components.BorderLayoutPanel
import java.awt.Color
import javax.swing.*
import javax.swing.event.DocumentEvent

/**
 * @author lfg
 * @version 1.0
 */
class InvokeDialog(var project: Project?, var psiMethod: PsiMethod) : DialogWrapper(project) {

    private val jTextMaxRow = 20
    private val jTextDefaultColumn = 85

    //参数map key：参数名，value：参数
    val argMap: LinkedHashMap<String, Arg> = LinkedHashMap()

    //预览invoke 文本区域
    private var preJTextArea = JTextArea(5, jTextDefaultColumn - 5)

    /**
     * 处理ok操作
     */
    override fun doOKAction() {
        super.doOKAction()
        CopyUtil.copy(preJTextArea.text)
    }

    /**
     * 处理验证
     */
    override fun doValidateAll(): List<ValidationInfo> {
        val result: MutableList<ValidationInfo> = ArrayList()
        argMap.values
            .stream()
            .filter { arg -> !MyPsiUtil.isIgnoreType(arg.psiParameter.type.presentableText) }
            .forEach { arg ->
                run {
                    if (!arg.jTextArea.text.isJsonString()) {
                        result.add(ValidationInfo("Json 格式有误", arg.jTextArea))
                    }
                }
            }
        return result
    }

    /**
     * 创建容器
     */
    override fun createCenterPanel(): JComponent {
        val panel = JPanel(VerticalLayout(20, SwingConstants.RIGHT))

        for (parameter in this.psiMethod.parameterList.parameters) {
            //入参
            panel.add(buildArgJPanel(parameter))
        }
        //指令预览
        panel.add(buildPreCommand())

        (this.okAction as DialogWrapper.OkAction).putValue("Name", "复制命令")
        val jbScrollPane = JBScrollPane(panel)
        jbScrollPane.border = SideBorder(Color.GRAY, 15, 0)
        jbScrollPane.verticalScrollBar.value = 1
        return jbScrollPane
    }

    /**
     * 构建单条参数 容器
     */
    private fun buildArgJPanel(parameter: PsiParameter): JPanel {
        val prettyJson = JsonUtil.prettyJson(JsonConverter.argToJsonObj(parameter.type))
        val nRows = StringUtil.appearNumber(prettyJson, "\n")
        val columns = if (nRows > jTextMaxRow) jTextDefaultColumn - 5 else jTextDefaultColumn

        val jTextArea = JTextArea(prettyJson, jTextMaxRow.coerceAtMost(nRows), columns)
        jTextArea.document.addDocumentListener(object : DocumentAdapter() {

            override fun textChanged(e: DocumentEvent) {
                try {
                    preJTextArea.text = DubboAdminInvoke.getInvokeCommand(psiMethod, argMap)
                } catch (_: Exception) {
                }
            }
        })
        argMap[parameter.name] = Arg(parameter, jTextArea)

        val parameterLabel = JLabel(parameter.name)
        parameterLabel.labelFor = jTextArea

        val jPanel = BorderLayoutPanel(5, 5)
        jPanel.addToLeft(parameterLabel)
        jPanel.addToRight(jTextArea)
        jPanel.addToCenter(JBScrollPane(jTextArea))

        return jPanel
    }

    /**
     * 构建预览命令容器
     */
    private fun buildPreCommand(): JPanel {
        preJTextArea.text = DubboAdminInvoke.getInvokeCommand(psiMethod, argMap)
        preJTextArea.lineWrap = true

        val label = JLabel("预览命令")
        label.labelFor = preJTextArea
        val jPanel = BorderLayoutPanel(5, 5)
        jPanel.addToLeft(label)
        jPanel.addToRight(preJTextArea)
        jPanel.addToCenter(JBScrollPane(preJTextArea))
        return jPanel
    }

    init {
        init()
    }

    class Arg(
        var psiParameter: PsiParameter,
        var jTextArea: JTextArea,
    )
}