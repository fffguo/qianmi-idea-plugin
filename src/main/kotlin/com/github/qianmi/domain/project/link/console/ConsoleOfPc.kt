package com.github.qianmi.domain.project.link.console

import com.github.qianmi.domain.project.AllProject

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
            return ConsoleOfPc(true, AllProject.defaultEnv.consoleOfPc.url)
        }
    }
}