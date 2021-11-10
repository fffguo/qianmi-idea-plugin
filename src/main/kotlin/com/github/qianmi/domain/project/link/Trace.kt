package com.github.qianmi.domain.project.link

import com.github.qianmi.domain.project.MyProject

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
            return Trace(true, MyProject.env.trace.url)
        }
    }
}