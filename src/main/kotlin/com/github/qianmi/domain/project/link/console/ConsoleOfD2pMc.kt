package com.github.qianmi.domain.project.link.console

class ConsoleOfD2pMc(
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
        fun defaultConsoleOfD2pMc(): ConsoleOfD2pMc {
            return ConsoleOfD2pMc(true, "")
        }
    }
}