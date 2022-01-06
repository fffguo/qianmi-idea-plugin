package com.github.qianmi.action.shell.ssh

import com.github.qianmi.action.shell.ShellSelectedAction
import com.github.qianmi.domain.project.tools.ShellElement
import com.github.qianmi.storage.EnvConfig
import com.intellij.openapi.actionSystem.AnActionEvent
import com.jetbrains.plugins.remotesdk.console.SshConsoleOptionsProvider
import com.jetbrains.plugins.remotesdk.console.SshTerminalCachingRunner
import org.jetbrains.plugins.terminal.TerminalTabState
import org.jetbrains.plugins.terminal.TerminalView


class SshSelectedAction(override var ele: ShellElement) : ShellSelectedAction() {

    override fun open(e: AnActionEvent) {
        val project = e.project!!

        val charset = SshConsoleOptionsProvider.getInstance(project).charset
        val runner = SshTerminalCachingRunner(project, ele.createSshUiData(), charset)
        //开启openssh
        TerminalView.getInstance(project).createNewSession(runner, buildTerminalTabState())
    }

    private fun buildTerminalTabState(): TerminalTabState {
        val state = TerminalTabState()
        state.myTabName = "${EnvConfig.getInstance().env.envName}(${ele.group})：${ele.ip}"
        state.myWorkingDirectory = ele.getWorkingDirectory()
        return state
    }
}