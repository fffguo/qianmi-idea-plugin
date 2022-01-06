package com.github.qianmi.action.shell

import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.domain.project.tools.ShellElement
import com.github.qianmi.storage.EnvConfig
import com.github.qianmi.util.CollectionUtil.isNotNullOrEmpty
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.popup.JBPopupFactory
import java.util.stream.Collectors


abstract class ShellAction<T : ShellSelectedAction> : AnAction() {

    abstract fun instanceShellSelectedAction(ele: ShellElement): T

    override fun actionPerformed(e: AnActionEvent) {
        val myProject = AllProject.currentProject(e)

        val eleList = myProject.shell.eleMap[EnvConfig.getInstance().env].orEmpty()
        if (eleList.isNotNullOrEmpty()) {
            if (eleList.size == 1) {
                instanceShellSelectedAction(eleList[0]).open(e)
                return
            }
        }

        val actionList = eleList.stream()
            .map { ele -> instanceShellSelectedAction(ele) }
            .peek { action ->
                action.templatePresentation.text = "${action.ele.group}: \n \n${action.ele.ip}(${action.ele.version})"
            }
            .collect(Collectors.toList())
        //创建popup
        JBPopupFactory.getInstance().createActionGroupPopup(
            "请选择节点",
            DefaultActionGroup(actionList),
            e.dataContext,
            JBPopupFactory.ActionSelectionAid.NUMBERING,
            true,
            null,
            -1
        ).showInFocusCenter()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = AllProject.currentProject(e).shell.isSupport
    }
}