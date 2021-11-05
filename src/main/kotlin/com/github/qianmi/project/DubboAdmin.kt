package com.github.qianmi.project

import com.github.qianmi.enums.DubboAdminEnum

private const val formatUrl = "{domain}"

class DubboAdmin {

    /**
     * 是否支持
     */
    var isSupport: Boolean = false

    /**
     * 类型：消费者/提供者
     */
    var dubboType: DubboTypeEnum = DubboTypeEnum.CONSUMER

    constructor()
    constructor(isSupport: Boolean, dubboType: DubboTypeEnum) {
        this.isSupport = isSupport
        this.dubboType = dubboType
    }

    /**
     * 获取 dubbo admin url
     */
    fun getDubboAdminUrl(env: DubboAdminEnum): String {
        return formatUrl.replace("{domain}", env.domain)
    }

    enum class DubboTypeEnum {
        PROVIDER, CONSUMER, ALL
    }
}