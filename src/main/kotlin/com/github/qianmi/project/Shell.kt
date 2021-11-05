package com.github.qianmi.project

import com.github.qianmi.config.EnvConfig
import com.github.qianmi.config.ShellConfig
import org.jetbrains.plugins.terminal.TerminalTabState
import java.nio.charset.Charset

//private const val formatUrl = "ssh -p{port} {username}@{ip}"

class Shell {

    var isSupportShell: Boolean = false

    /**
     * ip 地址
     */
    var shellIp: String = "172.21.4.55"

    /**
     * 用户名
     */
    var shellUserName: String = ShellConfig.username

    /**
     * 密码
     */
    var shellPassword: String = ShellConfig.password

    /**
     * 端口
     */
    var shellPort: Int = ShellConfig.port

    /**
     * 工作路径
     */
    var workingDirectory: String = ShellConfig.workingDirectory

    constructor(isSupportShell: Boolean, shellIp: String, workingDirectory: String) {
        this.isSupportShell = isSupportShell
        this.shellIp = shellIp
        this.workingDirectory = workingDirectory
    }

    constructor()


//    /**
//     * 获取 sh 命令
//     */
//    fun getShellCommand(): String {
//        return formatUrl
//            .replace("{port}", shellPort)
//            .replace("{username}", shellUserName)
//            .replace("{ip}", shellIp)
//    }

    fun buildTerminalTabState(): TerminalTabState {
        val state = TerminalTabState()
        state.myTabName = EnvConfig.getCurrentEnv().code + "：" + this.shellIp
        state.myWorkingDirectory = this.workingDirectory
        return state
    }

    fun getShellCharset(): Charset {
        return Charset.forName("utf-8")
    }

}