package com.github.qianmi.action.link

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ApmDashboardAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(com.github.qianmi.infrastructure.domain.project.AllProject.currentProject(e).apmDashboard.url)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled =
            com.github.qianmi.infrastructure.domain.project.AllProject.currentProject(e).apmDashboard.isSupport
    }

}