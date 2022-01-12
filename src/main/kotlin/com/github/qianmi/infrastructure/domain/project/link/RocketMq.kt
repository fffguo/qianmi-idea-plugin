package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.storage.EnvConfig

class RocketMq(
    /**
     * 是否支持
     */
    var isSupport: Boolean,

    /**
     * 地址
     */
    var url: String,
) {
    companion object {
        fun defaultRocketMq(): RocketMq {
            return RocketMq(true, EnvConfig.getInstance().env.rocketMq.url)
        }
    }
}