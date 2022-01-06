package com.github.qianmi.domain.project.link

import com.github.qianmi.storage.EnvConfig

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