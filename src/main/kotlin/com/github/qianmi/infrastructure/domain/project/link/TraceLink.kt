package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project

class TraceLink(override var isSupport: Boolean) : BaseLink() {


    override fun getBrowserUrl(project: Project): String {
        return when (EnvConfig.getInstance().env) {
            EnvEnum.LAKALA -> ""
            EnvEnum.TEST0 -> ""
            EnvEnum.TEST1 -> "http://tracet1.dev.qianmi.com/search"
            EnvEnum.TEST4 -> ""
            EnvEnum.TEST5 -> ""
            EnvEnum.TEST6 -> ""
            EnvEnum.TEST7 -> ""
            EnvEnum.DEV -> ""
            EnvEnum.PROD -> "http://trace.dev.qianmi.com/search"
        }
    }

    override fun getLinkName(): String {
        return "Trace Link"
    }


    companion object {

        private val instance = TraceLink(true)

        fun getInstance(): TraceLink {
            return instance
        }
    }
}