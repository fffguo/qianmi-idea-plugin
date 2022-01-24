package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project

class RocketMqLink(override var isSupport: Boolean) : BaseLink() {


    override fun getBrowserUrl(project: Project): String {
        return when (EnvConfig.getInstance().env) {
            EnvEnum.LAKALA -> "http://10.7.65.127:8080/#/topic"
            EnvEnum.TEST0 -> "http://172.19.67.236:8080/#/topic"
            EnvEnum.TEST1 -> "http://172.21.34.235:8080/#/topic"
            EnvEnum.TEST2 -> "http://172.21.4.241:8080/#/topic"
            EnvEnum.TEST4 -> "http://172.21.36.197:8080/#/topic"
            EnvEnum.TEST5 -> "http://172.21.33.247:8080/#/topic"
            EnvEnum.TEST6 -> "http://172.19.32.145:8080/#/topic"
            EnvEnum.TEST7 -> ""
            EnvEnum.DEV -> "http://172.19.72.105:8080/#/topic"
            EnvEnum.PROD -> ""
        }
    }

    override fun getLinkName(): String {
        return "RocketMq Link"
    }

    override fun getBugattiProject(): BugattiProjectEnum {
        return BugattiProjectEnum.ROCKET_MQ_ADMIN
    }

    companion object {

        private var instance = RocketMqLink(true)

        fun getInstance(): RocketMqLink {
            return instance
        }
    }
}