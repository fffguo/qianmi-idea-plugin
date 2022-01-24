package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.link.ActiveMqLink
import com.github.qianmi.infrastructure.domain.project.link.BaseLink

class ActiveMqAction : BaseLinkAction() {

    override fun getLinkProject(): BaseLink {
        return ActiveMqLink.getInstance()
    }

}