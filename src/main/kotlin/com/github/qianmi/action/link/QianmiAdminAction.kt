package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.QianmiAdminLink

class QianmiAdminAction : BaseLinkAction() {

    override fun getLinkProject(): BaseLink {
        return QianmiAdminLink.getInstance()
    }

}