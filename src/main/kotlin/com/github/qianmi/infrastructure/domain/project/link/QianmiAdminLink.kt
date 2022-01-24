package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project

class QianmiAdminLink(override var isSupport: Boolean) : BaseLink() {


    override fun getBrowserUrl(project: Project): String {
        return when (EnvConfig.getInstance().env) {
            EnvEnum.LAKALA -> "http://www.1000.com.lakala.ck/#/main/"
            EnvEnum.TEST0 -> "http://web.1000.com.test0.ck/#/main/"
            EnvEnum.TEST1 -> "http://web.1000.com.test1.ck/#/main/"
            EnvEnum.TEST2 -> "http://web.1000.com.test2.ck/#/main/"
            EnvEnum.TEST4 -> "http://web.1000.com.test4.ck/#/main/"
            EnvEnum.TEST5 -> "http://web.1000.com.test5.ck/#/main/"
            EnvEnum.TEST6 -> "http://web.1000.com.test6.ck/#/main/"
            EnvEnum.TEST7 -> "http://web.1000.com.test7.ck/#/main/"
            EnvEnum.DEV -> ""
            EnvEnum.PROD -> "https://www.1000.com/"
        }
    }

    override fun getLinkName(): String {
        return "Qianmi Admin Link"
    }

    companion object {
        private val instance = QianmiAdminLink(true)

        fun getInstance(): QianmiAdminLink {
            return instance
        }
    }

}