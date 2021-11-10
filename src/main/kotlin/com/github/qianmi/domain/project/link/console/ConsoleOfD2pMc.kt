package com.github.qianmi.domain.project.link.console

import com.github.qianmi.domain.project.MyProject

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
            return ConsoleOfD2pMc(true, MyProject.env.consoleOfD2pMc.url)
        }
    }
}