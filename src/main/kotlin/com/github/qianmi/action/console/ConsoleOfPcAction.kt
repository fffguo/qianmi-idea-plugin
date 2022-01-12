package com.github.qianmi.action.console

import com.github.qianmi.infrastructure.domain.project.AllProject
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ConsoleOfPcAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(AllProject.currentProject(e).consoleOfPc.url)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = AllProject.currentProject(e).consoleOfPc.isSupport
    }

}