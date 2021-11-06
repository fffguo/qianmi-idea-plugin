package com.github.qianmi.project

private const val formatUrl = "{domain}"

class DubboAdmin {

    /**
     * 是否支持
     */
    var isSupport: Boolean

    /**
     * 类型：消费者/提供者
     */
    var dubboType: DubboTypeEnum

    /**
     * 域名
     */
    var domain: String

    constructor(isSupport: Boolean, dubboType: DubboTypeEnum, domain: String) {
        this.isSupport = isSupport
        this.dubboType = dubboType
        this.domain = domain
    }


    companion object {
        fun defaultDubboAdmin(): DubboAdmin {
            return DubboAdmin(false, DubboTypeEnum.NONE, MyProject.env.dubboAdmin.domain)
        }
    }


    /**
     * 获取 dubbo admin url
     */
    fun getDubboAdminUrl(): String {
        return formatUrl.replace("{domain}", domain)
    }

    enum class DubboTypeEnum {
        PROVIDER, CONSUMER, ALL, NONE
    }
}