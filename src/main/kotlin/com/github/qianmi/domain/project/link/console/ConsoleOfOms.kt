package com.github.qianmi.domain.project.link.console

import com.github.qianmi.storage.EnvConfig

class ConsoleOfOms(
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
        fun defaultConsoleOfOms(): ConsoleOfOms {
            return ConsoleOfOms(true, EnvConfig.getInstance().env.consoleOfOms.url)
        }
    }
}