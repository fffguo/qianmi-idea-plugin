package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.domain.enums.EnvEnum.*
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project

class DubboAdminLink(override var isSupport: Boolean) : BaseLink() {


    override fun getBrowserUrl(project: Project): String {
        return when (EnvConfig.getInstance().env) {
            LAKALA -> "http://10.7.64.121:8080"
            TEST0 -> "http://172.19.67.104:8080"
            TEST1 -> "http://172.19.66.25:8080"
            TEST4 -> "http://172.19.68.8:8080"
            TEST5 -> "http://172.21.33.162:8080"
            TEST6 -> "http://172.19.32.74:8080"
            TEST7 -> ""
            DEV -> "http://172.19.65.13:8080"
            PROD -> "http:dubbomanager.dev.qianmi.com"
        }
    }

    override fun getLinkName(): String {
        return "Dubbo Admin Link"
    }

    override fun getBugattiProject(): BugattiProjectEnum {
        return BugattiProjectEnum.DUBBO_ADMIN
    }

    companion object {
        private var instance = DubboAdminLink(true)

        fun getInstance(): DubboAdminLink {
            return instance
        }

    }
}