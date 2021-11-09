package com.github.qianmi.action.shell

import cn.hutool.core.collection.CollectionUtil
import com.github.qianmi.domain.project.MyProject
import com.github.qianmi.services.ShellInitService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.ui.popup.JBPopupFactory
import java.util.stream.Collectors


class ShellAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        //同步节点配置
        if (MyProject.shell.isNeedSyncEle) {
            ShellInitService.syncShellElement()
        }

        val eleList = MyProject.shell.eleList
        if (CollectionUtil.isNotEmpty(eleList)) {
            if (eleList.size == 1) {
                eleList[0].openSshTerminal(e.project!!)
                return
            }
        }
        val formatStr = "%s: %s(%s)"
        val actionList = eleList.stream()
            .map { ele -> ShellSelectedAction(ele) }
            .peek { action ->
                action.templatePresentation.text =
                    String.format(formatStr, action.ele.group, action.ele.ip, action.ele.version)
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
        e.presentation.isEnabledAndVisible = MyProject.shell.isSupportShell
    }


}