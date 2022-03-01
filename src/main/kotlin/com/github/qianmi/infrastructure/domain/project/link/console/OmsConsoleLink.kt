package com.github.qianmi.infrastructure.domain.project.link.console

import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project

class OmsConsoleLink(override var isSupport: Boolean) : BaseLink() {

    override fun getBrowserUrl(project: Project): String {
        return when (EnvConfig.getInstance().env) {
            EnvEnum.LAKALA -> ""
            EnvEnum.TEST0 -> "http://172.21.36.109:8080/#/"
            EnvEnum.TEST1 -> "http://172.21.34.139:8080/#/"
            EnvEnum.TEST4 -> "http://172.19.68.123:8080/#/"
            EnvEnum.TEST5 -> "http://172.21.33.20:8080/#/"
            EnvEnum.TEST6 -> "http://172.19.33.45:8080"
            EnvEnum.TEST7 -> ""
            EnvEnum.DEV -> "http://172.19.65.114:8080/#/"
            EnvEnum.PROD -> "http://oms-console.crm.qianmi.com/"
        }
    }

    override fun getLinkName(): String {
        return "Oms Console Link"
    }

    override fun getBugattiProject(): BugattiProjectEnum {
        return BugattiProjectEnum.OMS
    }

    companion object {

        private val instance = OmsConsoleLink(true)

        fun getInstance(): OmsConsoleLink {
            return instance

        }
    }
}