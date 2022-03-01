package com.github.qianmi.infrastructure.domain.project.link.console

import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project

class PcMsConsoleLink(override var isSupport: Boolean) : BaseLink() {

    override fun getBrowserUrl(project: Project): String {
        return when (EnvConfig.getInstance().env) {
            EnvEnum.LAKALA -> ""
            EnvEnum.TEST0 -> "http://172.21.36.47:8080/module/home"
            EnvEnum.TEST1 -> "http://172.19.66.197:8080/module/home"
            EnvEnum.TEST4 -> "http://172.19.68.29:8080/module/home"
            EnvEnum.TEST5 -> "http://172.21.33.153:8080/module/home"
            EnvEnum.TEST6 -> "http://172.19.32.192:8080"
            EnvEnum.TEST7 -> ""
            EnvEnum.DEV -> "http://172.19.65.203:8080/module/home"
            EnvEnum.PROD -> ""
        }
    }

    override fun getLinkName(): String {
        return "PC Console Link"
    }

    override fun getBugattiProject(): BugattiProjectEnum? {
        return BugattiProjectEnum.PC_MS
    }

    companion object {

        private val instance = PcMsConsoleLink(true)

        fun getInstance(): PcMsConsoleLink {
            return instance
        }
    }

}