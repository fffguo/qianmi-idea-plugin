package com.github.qianmi.project

import com.github.qianmi.enums.DubboAdminEnum

private const val formatUrl = "{domain}"

interface DubboAdmin {

    /**
     * 类型：消费者/提供者
     */
    val dubboType: DubboTypeEnum

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