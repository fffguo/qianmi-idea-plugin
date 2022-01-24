package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.JenkinsLink

class JenkinsAction : BaseLinkAction() {

    override fun getLinkProject(): BaseLink {
        return JenkinsLink.getInstance()
    }

}