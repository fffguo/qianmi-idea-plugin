package com.github.qianmi.infrastructure.domain.project.link.console

import com.github.qianmi.infrastructure.storage.EnvConfig

class ConsoleOfPc(
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
        fun defaultConsoleOfPc(): ConsoleOfPc {
            return ConsoleOfPc(true, EnvConfig.getInstance().env.consoleOfPc.url)
        }
    }
}