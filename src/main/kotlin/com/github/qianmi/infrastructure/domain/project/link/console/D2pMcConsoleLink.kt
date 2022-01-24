package com.github.qianmi.infrastructure.domain.project.link.console

import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project

class D2pMcConsoleLink(override var isSupport: Boolean) : BaseLink() {


    override fun getBrowserUrl(project: Project): String {
        return when (EnvConfig.getInstance().env) {
            EnvEnum.LAKALA -> ""
            EnvEnum.TEST0 -> "http://172.19.67.123:8080/#/"
            EnvEnum.TEST1 -> "http://172.21.3.4:8080/#/"
            EnvEnum.TEST2 -> "http://172.21.34.104:8080/#/"
            EnvEnum.TEST4 -> "http://172.19.68.165:8080/#/"
            EnvEnum.TEST5 -> "http://172.19.69.87:8080/#/"
            EnvEnum.TEST6 -> "http://172.19.32.198:8080"
            EnvEnum.TEST7 -> ""
            EnvEnum.DEV -> "http://172.19.72.47:8080/#/"
            EnvEnum.PROD -> "http://172.17.18.248:8080/#/"
        }
    }

    override fun getLinkName(): String {
        return "D2pMc Console Link"
    }

    override fun getBugattiProject(): BugattiProjectEnum {
        return BugattiProjectEnum.D2P_MC_CONSOLE
    }

    companion object {

        private val instance = D2pMcConsoleLink(true)

        fun getInstance(): D2pMcConsoleLink {
            return instance
        }
    }

}