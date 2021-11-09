package com.github.qianmi.domain.project

import com.github.qianmi.config.ShellConfig
import com.github.qianmi.services.vo.BugattiShellInfoResult
import com.intellij.openapi.project.Project
import com.intellij.remote.AuthType
import com.intellij.ssh.config.unified.SshConfig
import com.intellij.ssh.ui.unified.SshUiData
import com.jetbrains.plugins.remotesdk.console.SshTerminalCachingRunner
import org.jetbrains.plugins.terminal.TerminalTabState
import org.jetbrains.plugins.terminal.TerminalView
import java.nio.charset.Charset
import java.util.*

class Shell(

    /**
     * 是否支持shell
     */
    var isSupportShell: Boolean,

    /**
     * 是否需要同步节点
     */
    var isNeedSyncEle: Boolean,

    /**
     * 节点列表
     */
    var eleList: List<Element>,
) {

    companion object {
        fun defaultShell(): Shell {
            return Shell(isSupportShell = false, isNeedSyncEle = false, eleList = Collections.emptyList())
        }
    }


    fun getShellCharset(): Charset {
        return Charset.forName("utf-8")
    }

    class Element {
        /**
         * ip 地址
         */
        var ip: String = "172.21.4.55"

        /**
         * 用户名
         */
        var userName: String = ShellConfig.username

        /**
         * 密码
         */
        var password: String = ShellConfig.password

        /**
         * 端口
         */
        var port: Int = ShellConfig.port

        /**
         * 工作路径
         */
        var workingDirectory: String = ShellConfig.workingDirectory

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
            val runner = SshTerminalCachingRunner(project, createSshUiData(), Charset.defaultCharset())
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