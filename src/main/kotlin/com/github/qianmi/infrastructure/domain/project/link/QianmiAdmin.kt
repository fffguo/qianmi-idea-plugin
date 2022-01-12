package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.storage.EnvConfig

class QianmiAdmin(
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
        fun defaultQianmiAdmin(): QianmiAdmin {
            return QianmiAdmin(true, EnvConfig.getInstance().env.qianmiAdmin.url)
        }
    }
}