package com.github.qianmi.action.shell.ssh

import com.github.qianmi.action.shell.ShellSelectedAction
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.domain.project.tools.ShellElement
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
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
        TerminalView.getInstance(project).createNewSession(runner, buildTerminalTabState(project))
    }

    private fun buildTerminalTabState(project: Project): TerminalTabState {
        val myProject = AllProject.currentProject(project)

        val state = TerminalTabState()
        state.myTabName = "${myProject.env.envName}(${ele.group})：${ele.ip}"
        state.myWorkingDirectory = ele.getWorkingDirectory()
        return state
    }
}