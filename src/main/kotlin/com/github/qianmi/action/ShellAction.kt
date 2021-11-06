package com.github.qianmi.action

import com.github.qianmi.project.MyProject
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.jetbrains.plugins.remotesdk.console.SshTerminalCachingRunner
import org.jetbrains.plugins.terminal.TerminalView
import java.nio.charset.Charset


class ShellAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val eleList = MyProject.shell.eleList
        if (eleList.size == 1) {
            val element = eleList[0]
            val runner = SshTerminalCachingRunner(e.project!!, element.createSshUiData(), Charset.defaultCharset())
            //开启openssh
            TerminalView.getInstance(e.project!!).createNewSession(runner, element.buildTerminalTabState())
        }
    }


    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = MyProject.shell.isSupportShell
    }

}