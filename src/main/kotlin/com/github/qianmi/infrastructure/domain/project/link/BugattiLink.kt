package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.project.Project


class BugattiLink(
    override var isSupport: Boolean,
    var code: String,
    var name: String,
) : BaseLink() {

    companion object {

        const val domain = "http://bugatti.dev.qianmi.com"

        private var bugattiLink: BugattiLink = BugattiLink(false, "", "")

        fun getInstance(): BugattiLink {
            return bugattiLink
        }

        fun instanceOfBugattiProject(project: BugattiProjectEnum): BugattiLink {
            return BugattiLink(true, project.code, "")
        }
    }

    override fun getBrowserUrl(project: Project): String {
        return "${domain}/#/list/${EnvConfig.getInstance().env.bugattiEnvCode}/info/${code}?txt=${name}"
    }

    override fun getLinkName(): String {
        return "Bugatti Link"
    }


}