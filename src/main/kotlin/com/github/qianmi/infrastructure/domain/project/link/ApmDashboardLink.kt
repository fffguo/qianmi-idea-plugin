package com.github.qianmi.infrastructure.domain.project.link

import com.intellij.openapi.project.Project

/**
 * apm 仪表盘（全链路大数据统计）
 */
class ApmDashboardLink(override var isSupport: Boolean) : BaseLink() {

    override fun getBrowserUrl(project: Project): String {
        return "http://apm.dev.qianmi.com/"
    }

    override fun getLinkName(): String {
        return "Apm Dashboard Link"
    }


    companion object {

        private val instance = ApmDashboardLink(true)

        fun getInstance(): ApmDashboardLink {
            return instance
        }
    }
}