package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.link.ApmDashboardLink
import com.github.qianmi.infrastructure.domain.project.link.BaseLink

class ApmDashboardAction : BaseLinkAction() {

    override fun getLinkProject(): BaseLink {
        return ApmDashboardLink.getInstance()
    }
}