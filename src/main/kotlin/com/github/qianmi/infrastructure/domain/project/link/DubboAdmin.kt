package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.storage.EnvConfig

class DubboAdmin(
    /**
     * 是否支持
     */
    var isSupport: Boolean,
    /**
     * 类型：消费者/提供者
     */
    var dubboType: DubboTypeEnum,
    /**
     * 地址
     */
    var url: String,
) {


    companion object {
        fun defaultDubboAdmin(): DubboAdmin {
            return DubboAdmin(true, DubboTypeEnum.NONE, EnvConfig.getInstance().env.dubboAdmin.url)
        }
    }

    enum class DubboTypeEnum {
        PROVIDER, CONSUMER, ALL, NONE
    }
}