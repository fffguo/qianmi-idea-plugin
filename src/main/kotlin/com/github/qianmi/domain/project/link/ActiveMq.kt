package com.github.qianmi.domain.project.link

import com.github.qianmi.storage.EnvConfig

class ActiveMq(
    /**
     * 是否支持
     */
    var isSupport: Boolean,

    /**
     * url
     */
    var url: String,
) {
    companion object {
        fun defaultActiveMq(): ActiveMq {
            return ActiveMq(true, EnvConfig.getInstance().env.activeMq.url)
        }
    }
}