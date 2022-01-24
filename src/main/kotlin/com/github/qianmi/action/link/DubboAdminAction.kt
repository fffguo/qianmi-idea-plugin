package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.DubboAdminLink

class DubboAdminAction : BaseLinkAction() {

    override fun getLinkProject(): BaseLink {
        return DubboAdminLink.getInstance()
    }

}