package com.github.qianmi.domain.project.link

import com.github.qianmi.domain.project.AllProject

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
            return RocketMq(true, AllProject.defaultEnv.rocketMq.url)
        }
    }
}