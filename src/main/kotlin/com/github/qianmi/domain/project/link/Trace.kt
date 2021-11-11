package com.github.qianmi.domain.project.link

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
            return Trace(true, "")
        }
    }
}