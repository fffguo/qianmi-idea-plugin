package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.GitlabLink

class GitlabAction : BaseLinkAction() {

    override fun getLinkProject(): BaseLink {
        return GitlabLink.getInstance()
    }

}