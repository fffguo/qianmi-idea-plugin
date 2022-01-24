package com.github.qianmi.action.shell

import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.domain.project.tools.ShellElement
import com.github.qianmi.infrastructure.extend.CollectionExtend.isNotEmpty
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.github.qianmi.infrastructure.util.BugattiHttpUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.popup.JBPopupFactory
import java.util.stream.Collectors


abstract class ShellAction<T : ShellSelectedAction> : AnAction() {

    abstract fun instanceShellSelectedAction(ele: ShellElement): T

    override fun actionPerformed(e: AnActionEvent) {
        val myProject = IdeaProject.getInstance(e)

        val eleList = BugattiHttpUtil.getShellElementList(myProject.bugattiLink.code)

        if (eleList.isNotEmpty()) {
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
        e.presentation.isEnabled = EnvConfig.getInstance().env != EnvEnum.PROD
    }
}