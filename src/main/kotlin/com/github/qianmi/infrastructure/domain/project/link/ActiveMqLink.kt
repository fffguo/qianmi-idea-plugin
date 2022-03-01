package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project

class ActiveMqLink(override var isSupport: Boolean) : BaseLink() {

    override fun getBrowserUrl(project: Project): String {
        return when (EnvConfig.getInstance().env) {
            EnvEnum.LAKALA -> "http://10.7.65.210:8161/admin/topics.jsp"
            EnvEnum.TEST0 -> "http://172.19.67.50:8161/admin/topics.jsp"
            EnvEnum.TEST1 -> "http://172.21.4.29:8161/admin/topics.jsp"
            EnvEnum.TEST4 -> "http://172.19.68.15:8161/admin/topics.jsp"
            EnvEnum.TEST5 -> "http://172.21.33.195:8161/admin/topics.jsp"
            EnvEnum.TEST6 -> "http://172.19.65.38:8161/admin/topics.jsp"
            EnvEnum.TEST7 -> ""
            EnvEnum.DEV -> ""
            EnvEnum.PROD -> "http://mq.dev.qianmi.com/admin/topics.jsp"
        }
    }

    override fun getLinkName(): String {
        return "Active MQ Link"
    }

    override fun getBugattiProject(): BugattiProjectEnum {
        return BugattiProjectEnum.ACTIVE_MQ_ADMIN
    }

    companion object {

        private var instance: ActiveMqLink = ActiveMqLink(true)

        fun getInstance(): ActiveMqLink {
            return instance
        }
    }
}