package com.github.qianmi.domain.project.link

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
            return QianmiAdmin(true, "")
        }
    }
}