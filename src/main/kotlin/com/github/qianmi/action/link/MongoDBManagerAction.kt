package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.MongoDBManagerLink

class MongoDBManagerAction : BaseLinkAction() {

    override fun getLinkProject(): BaseLink {
        return MongoDBManagerLink.getInstance()
    }
}