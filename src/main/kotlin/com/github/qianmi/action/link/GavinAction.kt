package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.GavinAdminLink

class GavinAction : BaseLinkAction() {

    override fun getLinkProject(myProject: IdeaProject.MyProject): BaseLink {
        return GavinAdminLink.getInstance()
    }

}