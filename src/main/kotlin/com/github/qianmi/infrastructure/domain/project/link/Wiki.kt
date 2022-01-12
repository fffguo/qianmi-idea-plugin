package com.github.qianmi.infrastructure.domain.project.link

class Wiki(
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
        fun defaultWiki(): Wiki {
            return Wiki(true, "http://wiki.dev.qianmi.com/")
        }
    }
}