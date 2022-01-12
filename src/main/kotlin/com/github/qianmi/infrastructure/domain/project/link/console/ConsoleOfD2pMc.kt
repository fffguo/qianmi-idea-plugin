package com.github.qianmi.infrastructure.domain.project.link.console

import com.github.qianmi.infrastructure.storage.EnvConfig

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
            return ConsoleOfD2pMc(true, EnvConfig.getInstance().env.consoleOfD2pMc.url)
        }
    }
}