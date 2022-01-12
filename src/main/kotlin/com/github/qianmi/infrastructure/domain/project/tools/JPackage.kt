package com.github.qianmi.infrastructure.domain.project.tools

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