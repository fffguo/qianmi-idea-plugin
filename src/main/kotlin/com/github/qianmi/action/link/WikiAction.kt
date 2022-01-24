package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.WikiLink

class WikiAction : BaseLinkAction() {

    override fun getLinkProject(): BaseLink {
        return WikiLink.getInstance()
    }

}