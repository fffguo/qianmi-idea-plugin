package com.github.qianmi.action.console

import com.github.qianmi.domain.project.MyProject
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ConsoleOfPcAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(MyProject.consoleOfPc.url)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = MyProject.consoleOfPc.isSupport
    }

}