package com.github.qianmi.project

import com.github.qianmi.config.ShellConfig
import com.github.qianmi.services.vo.BugattiShellInfoResult
import com.intellij.remote.AuthType
import com.intellij.ssh.config.unified.SshConfig
import com.intellij.ssh.ui.unified.SshUiData
import org.jetbrains.plugins.terminal.TerminalTabState
import java.nio.charset.Charset
import java.util.*

//private const val formatUrl = "ssh -p{port} {username}@{ip}"

class Shell {

    var isSupportShell: Boolean

    var eleList: List<Element>

    constructor(isSupportShell: Boolean, eleList: List<Element>) {
        this.isSupportShell = isSupportShell
        this.eleList = eleList
    }


    companion object {
        fun defaultShell(): Shell {
            return Shell(false, Collections.emptyList())
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

        fun buildTerminalTabState(): TerminalTabState {
            val state = TerminalTabState()
            state.myTabName = group + "：" + this.ip
            state.myWorkingDirectory = this.workingDirectory
            return state
        }

        fun createSshUiData(): SshUiData {
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

        companion object {
            fun instanceOf(result: BugattiShellInfoResult): Element {
                val element = Element()
                element.ip = result.ip
                element.group = result.group
                element.version = result.version
                return element
            }
        }
    }

}