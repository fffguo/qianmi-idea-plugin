package com.github.qianmi.domain.project.link

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
            return ActiveMq(true, "")
        }
    }
}