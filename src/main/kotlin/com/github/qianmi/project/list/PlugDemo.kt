package com.github.qianmi.project.list

import com.github.qianmi.config.ShellConfig
import com.github.qianmi.project.DubboAdmin
import com.github.qianmi.project.Project

object PlugDemo : Project() {

    override val name: String
        get() = "plugin-demo"
    override val desc: String
        get() = "云采购小程序BFF"
    override val bugattiProjectCode: String
        get() = "1403"
    override val bugattiProjectName: String
        get() = "purchase_wx_bff"
    override val gitlabGroup: String
        get() = "gcs"
    override val gitlabProjectName: String
        get() = "purchase-wx-bff"
    override val dubboType: DubboAdmin.DubboTypeEnum
        get() = DubboAdmin.DubboTypeEnum.CONSUMER
    override val isSupportShell: Boolean
        get() = true
    override val shellIp: String
        get() = "172.21.4.55"
    override val shellUserName: String
        get() = ShellConfig.username
    override val shellPassword: String
        get() = ShellConfig.password
    override val shellPort: String
        get() = ShellConfig.port
}