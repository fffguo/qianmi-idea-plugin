package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.AllProject
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DubboAdminAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(AllProject.currentProject(e).dubboAdmin.url)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = AllProject.currentProject(e).dubboAdmin.isSupport
    }


}