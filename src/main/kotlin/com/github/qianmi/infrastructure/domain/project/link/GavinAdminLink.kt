package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project

class GavinAdminLink(override var isSupport: Boolean) : BaseLink() {

    override fun getBrowserUrl(project: Project): String {
        return when (EnvConfig.getInstance().env) {
            EnvEnum.LAKALA -> "http://10.7.65.139:8080/module/home"
            EnvEnum.TEST0 -> "http://172.21.36.4:8080/module/home"
            EnvEnum.TEST1 -> "http://172.19.66.177:8080/module/home"
            EnvEnum.TEST2 -> "http://172.21.3.3:8080/module/home"
            EnvEnum.TEST4 -> "http://172.19.68.118:8080/module/home"
            EnvEnum.TEST5 -> "http://172.21.33.154:8080/module/home"
            EnvEnum.TEST6 -> "http://172.19.32.141:8080/module/home"
            EnvEnum.TEST7 -> ""
            EnvEnum.DEV -> "http://172.19.77.74:8080/module/home"
            EnvEnum.PROD -> ""
        }
    }

    override fun getLinkName(): String {
        return "Gavin Link"
    }

    override fun getBugattiProject(): BugattiProjectEnum {
        return BugattiProjectEnum.GAVIN_ADMIN
    }

    companion object {

        private val instance = GavinAdminLink(true)

        fun getInstance(): GavinAdminLink {
            return instance
        }
    }
}