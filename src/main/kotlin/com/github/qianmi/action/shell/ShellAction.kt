package com.github.qianmi.action.shell

import cn.hutool.core.collection.CollectionUtil
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.services.ShellInitService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.popup.JBPopupFactory
import java.util.stream.Collectors


class ShellAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val myProject = AllProject.currentProject(e)
        //同步节点配置
        if (myProject.shell.isNeedSyncEle) {
            ShellInitService.syncShellElement(myProject)
        }

        val eleList = myProject.shell.eleList
        if (CollectionUtil.isNotEmpty(eleList)) {
            if (eleList.size == 1) {
                eleList[0].openSshTerminal(e.project!!)
                return
            }
        }
        val actionList = eleList.stream()
            .map { ele -> ShellSelectedAction(ele) }
            .peek { action ->
                action.templatePresentation.text =
                    String.format("%s: %s(%s)", action.ele.group, action.ele.ip, action.ele.version)
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
        e.presentation.isEnabled = AllProject.currentProject(e).shell.isSupportShell
    }


}