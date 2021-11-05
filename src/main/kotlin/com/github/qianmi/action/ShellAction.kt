package com.github.qianmi.action

import com.github.qianmi.project.Shell
import com.github.qianmi.util.ProjectUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.remote.AuthType
import com.intellij.ssh.config.unified.SshConfig
import com.intellij.ssh.ui.unified.SshUiData
import com.intellij.ui.AppUIUtil
import com.jetbrains.plugins.remotesdk.console.SshTerminalCachingRunner
import org.jetbrains.plugins.terminal.TerminalView


class ShellAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val shell = ProjectUtil.getProject(e).shell

        val runner = SshTerminalCachingRunner(e.project!!, createSshUiData(shell), shell.getShellCharset())
        runner.connect()
        //开启openssh
        AppUIUtil.invokeLaterIfProjectAlive(e.project!!) {
            TerminalView.getInstance(e.project!!)
                .createNewSession(runner, shell.buildTerminalTabState())
        }
    }


    private fun createSshUiData(shell: Shell): SshUiData {
        val password = SshConfig.AuthData.create(shell.shellPassword, null, false, false)
        val sshConfig = SshConfig.create(
            true,
            shell.shellIp,
            shell.shellPort,
            shell.shellUserName,
            AuthType.PASSWORD,
            null
        )
        sshConfig.saveAuthDataToPasswordSafe(password)
        return SshUiData(sshConfig)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = ProjectUtil.getProject(e).shell.isSupportShell
    }

}