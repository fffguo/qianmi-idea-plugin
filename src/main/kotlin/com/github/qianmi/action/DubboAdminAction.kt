package com.github.qianmi.action

import com.github.qianmi.config.EnvConfig
import com.github.qianmi.util.ProjectUtil
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DubboAdminAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val dubboAdmin = ProjectUtil.getProject(e).dubboAdmin
        BrowserUtil.open(dubboAdmin.getDubboAdminUrl(EnvConfig.getDubboAdminEnum()))
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = ProjectUtil.getProject(e).dubboAdmin.isSupport
    }


}