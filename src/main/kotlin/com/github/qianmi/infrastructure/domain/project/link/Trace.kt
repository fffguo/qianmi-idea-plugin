package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.storage.EnvConfig

class Trace(
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
        fun defaultTrace(): Trace {
            return Trace(true, EnvConfig.getInstance().env.trace.url)
        }
    }
}