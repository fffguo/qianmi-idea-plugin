package com.github.qianmi.action

import com.github.qianmi.enums.DubboAdminEnum
import com.github.qianmi.util.ProjectUtil
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DubboAdminAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(ProjectUtil.getProject(e)!!.getDubboAdminUrl(DubboAdminEnum.TEST2))
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = ProjectUtil.getProject(e) != null
//        e.presentation.icon = QianmiIconUtil.bugatti
    }


}