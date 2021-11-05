package com.github.qianmi.project.list

import com.github.qianmi.config.ShellConfig
import com.github.qianmi.project.*

object D2pAdminApi : QianmiProject() {

    override val name: String
        get() = "d2p-admin-bff"
    override val desc: String
        get() = "云分销Bff"
    override val bugatti: Bugatti
        get() = Bugatti(true, "598", "admin_d2p_api")
    override val gitlab: Gitlab
        get() = Gitlab(true, "gcs", "d2p-admin-bff")
    override val dubboAdmin: DubboAdmin
        get() = DubboAdmin(true, DubboAdmin.DubboTypeEnum.CONSUMER)
    override val shell: Shell
        get() = Shell(true, "172.21.4.55", ShellConfig.workingDirectory)

}