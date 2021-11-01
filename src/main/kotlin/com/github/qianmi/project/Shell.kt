package com.github.qianmi.project

private const val formatUrl = "ssh -p{port} {username}@{ip} || ofcard"

interface Shell {

    val isSupportShell: Boolean

    /**
     * ip 地址
     */
    val shellIp: String

    /**
     * 用户名
     */
    val shellUserName: String

    /**
     * 密码
     */
    val shellPassword: String

    /**
     * 端口
     */
    val shellPort: String

    /**
     * 获取 sh 命令
     */
    fun getShellCommand(): String {
        return formatUrl
            .replace("{port}", shellPort)
            .replace("{username}", shellUserName)
            .replace("{ip}", shellIp)
    }

}