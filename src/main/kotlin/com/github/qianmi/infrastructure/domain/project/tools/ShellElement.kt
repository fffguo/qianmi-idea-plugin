package com.github.qianmi.infrastructure.domain.project.tools

import com.github.qianmi.infrastructure.domain.vo.BugattiShellInfoResult
import com.github.qianmi.infrastructure.storage.ShellConfig
import com.intellij.remote.AuthType
import com.intellij.ssh.config.unified.SshConfig
import com.intellij.ssh.ui.unified.SshUiData

class ShellElement {

    /**
     * hostId
     */
    var id: String = ""

    /**
     * ip 地址
     */
    var ip: String = ""

    /**
     * 用户名
     */
    private fun getUserName(): String = ShellConfig.getInstance().userName

    /**
     * 密码
     */
    private fun getPassword(): String = ShellConfig.getInstance().passwd

    /**
     * 端口
     */
    private fun getPort(): Int = ShellConfig.getInstance().port.toInt()

    /**
     * 工作路径
     */
    fun getWorkingDirectory(): String = ShellConfig.getInstance().dir

    /**
     * 分组名称
     */
    var group: String = "default"

    /**
     * 版本
     */
    var version: String = ""

    /**
     * 标签
     */
    var tag: String = ""

    /**
     * 状态
     */
    var state: String = ""

    companion object {
        fun instanceOf(result: BugattiShellInfoResult): ShellElement {
            val element = ShellElement()
            element.id = result.id
            element.ip = result.ip
            element.group = result.group
            element.version = result.version
            element.tag = result.tag
            element.state = result.state
            return element
        }
    }

    fun createSshUiData(): SshUiData {
        val password = SshConfig.AuthData.create(getPassword(), null, true, false)
        val sshConfig = SshConfig.create(true, ip, getPort(), getUserName(), AuthType.PASSWORD, null)
        sshConfig.saveAuthDataToPasswordSafe(password)
        return SshUiData(sshConfig)
    }


}