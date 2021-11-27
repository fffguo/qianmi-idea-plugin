package com.github.qianmi.domain.project.tools

import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.services.vo.BugattiShellInfoResult
import com.github.qianmi.storage.ShellConfig
import com.intellij.openapi.project.Project
import com.intellij.remote.AuthType
import com.intellij.ssh.config.unified.SshConfig
import com.intellij.ssh.ui.unified.SshUiData
import com.jetbrains.plugins.remotesdk.console.SshConsoleOptionsProvider
import com.jetbrains.plugins.remotesdk.console.SshTerminalCachingRunner
import org.jetbrains.plugins.terminal.TerminalTabState
import org.jetbrains.plugins.terminal.TerminalView
import java.nio.charset.Charset

class Shell(

    /**
     * 是否支持shell
     */
    var isSupport: Boolean,

    /**
     * 节点信息
     */
    var eleMap: HashMap<EnvEnum, List<Element>>,
) {

    companion object {
        fun defaultShell(): Shell {
            return Shell(isSupport = false, eleMap = hashMapOf(AllProject.defaultEnv to listOf()))
        }
    }


    fun getShellCharset(): Charset {
        return Charset.forName("utf-8")
    }

    class Element {
        /**
         * ip 地址
         */
        var ip: String = ""

        /**
         * 用户名
         */
        var userName: String = ShellConfig.getInstance().userName

        /**
         * 密码
         */
        var password: String = ShellConfig.getInstance().passwd

        /**
         * 端口
         */
        var port: Int = ShellConfig.getInstance().port

        /**
         * 工作路径
         */
        var workingDirectory: String = ShellConfig.getInstance().dir

        /**
         * 分组名称
         */
        var group: String = "default"

        /**
         * 版本
         */
        var version: String = ""

        companion object {
            fun instanceOf(result: BugattiShellInfoResult): Element {
                val element = Element()
                element.ip = result.ip
                element.group = result.group
                element.version = result.version
                return element
            }
        }

        fun openSshTerminal(project: Project) {
            val charset = SshConsoleOptionsProvider.getInstance(project).charset
            val runner = SshTerminalCachingRunner(project, createSshUiData(), charset)
            //开启openssh
            TerminalView.getInstance(project).createNewSession(runner, buildTerminalTabState())
        }

        private fun buildTerminalTabState(): TerminalTabState {
            val state = TerminalTabState()
            state.myTabName = group + "：" + this.ip
            state.myWorkingDirectory = this.workingDirectory
            return state
        }

        private fun createSshUiData(): SshUiData {
            val password = SshConfig.AuthData.create(password, null, false, false)
            val sshConfig = SshConfig.create(
                true,
                ip,
                port,
                userName,
                AuthType.PASSWORD,
                null
            )
            sshConfig.saveAuthDataToPasswordSafe(password)
            return SshUiData(sshConfig)
        }

    }

}