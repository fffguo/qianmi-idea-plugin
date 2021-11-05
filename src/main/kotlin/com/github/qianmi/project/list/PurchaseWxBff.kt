package com.github.qianmi.project.list

import com.github.qianmi.config.ShellConfig
import com.github.qianmi.project.*

object PurchaseWxBff : QianmiProject() {

    override val name: String
        get() = "purchase-wx-bff"
    override val desc: String
        get() = "云采购小程序BFF"
    override val bugatti: Bugatti
        get() = Bugatti(true, "1403", "purchase_wx_bff")
    override val gitlab: Gitlab
        get() = Gitlab(true, "gcs", "purchase-wx-bff")
    override val dubboAdmin: DubboAdmin
        get() = DubboAdmin(true, DubboAdmin.DubboTypeEnum.CONSUMER)
    override val shell: Shell
        get() = Shell(true, "172.21.4.55", ShellConfig.workingDirectory)

}