package com.github.qianmi.action.shell.ssh.command

import com.github.qianmi.action.shell.ShellSelectedAction
import com.github.qianmi.infrastructure.domain.project.tools.ShellElement
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.jetbrains.plugins.remotesdk.console.SshConsoleOptionsProvider
import com.jetbrains.plugins.remotesdk.console.SshTerminalCachingRunner
import org.jetbrains.plugins.terminal.TerminalTabState
import org.jetbrains.plugins.terminal.TerminalView
import java.io.OutputStream
import java.nio.charset.StandardCharsets

class SshCommandSelectedAction(override var ele: ShellElement, var getCommandString: (Project) -> String) :
    ShellSelectedAction() {


    override fun open(e: AnActionEvent) {
        val project = e.project!!

        val charset = SshConsoleOptionsProvider.getInstance(project).charset
        val runner = SshTerminalCachingRunner(project, ele.createSshUiData(), charset)
        //开启openssh
        TerminalView.getInstance(project).createNewSession(runner, buildTerminalTabState())
        //线程池执行，避免阻塞：createShellChannel会报 Blocking SSH method called from the dispatch or write thread
        ApplicationManager.getApplication().executeOnPooledThread {
            //反射获取shellChanel
            val declaredMethod = SshTerminalCachingRunner::class.java.getDeclaredMethod("getShellChannel")
            declaredMethod.isAccessible = true
            val shellChannel = declaredMethod.invoke(runner)

            val outputStream: OutputStream = Class.forName("com.intellij.ssh.impl.sshj.channels.SshjSshChannel")
                .getDeclaredMethod("getOutputStream")
                .invoke(shellChannel) as OutputStream

            //\n代表执行
            outputStream.write("${getCommandString(project)}\n".toByteArray(StandardCharsets.UTF_8))
            outputStream.flush()
        }

    }

    private fun buildTerminalTabState(): TerminalTabState {
        val state = TerminalTabState()
        state.myTabName = "${EnvConfig.getInstance().env.envName}(${ele.group})：${ele.ip}"
        return state
    }
}