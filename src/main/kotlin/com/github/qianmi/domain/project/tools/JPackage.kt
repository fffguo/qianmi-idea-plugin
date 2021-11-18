package com.github.qianmi.domain.project.tools

class JPackage(

    /**
     * 是否支持
     */
    var isSupport: Boolean,

    ) {

    companion object {
        fun defaultJPackage(): JPackage {
            return JPackage(false)
        }
    }

}