package com.github.qianmi.domain.project.link.console

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
            return ConsoleOfPc(true, "")
        }
    }
}