package com.github.qianmi.project.list

import com.github.qianmi.config.ShellConfig
import com.github.qianmi.project.DubboAdmin
import com.github.qianmi.project.Project

object D2pAdminApi : Project() {

    override val name: String
        get() = "d2p-admin-bff"
    override val desc: String
        get() = "云分销Bff"
    override val bugattiProjectCode: String
        get() = "598"
    override val bugattiProjectName: String
        get() = "admin_d2p_api"
    override val gitlabGroup: String
        get() = "gcs"
    override val gitlabProjectName: String
        get() = "d2p-admin-bff"
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