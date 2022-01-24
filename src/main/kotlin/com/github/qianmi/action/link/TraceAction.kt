package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.TraceLink

class TraceAction : BaseLinkAction() {

    override fun getLinkProject(): BaseLink {
        return TraceLink.getInstance()
    }

}