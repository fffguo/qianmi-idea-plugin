package com.github.qianmi.action.console

import com.github.qianmi.domain.project.MyProject
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ConsoleOfD2pMcAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(MyProject.consoleOfD2pMc.url)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = MyProject.consoleOfD2pMc.isSupport
    }

}