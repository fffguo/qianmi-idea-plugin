package com.github.qianmi.action.link.console

import com.github.qianmi.action.link.BaseLinkAction
import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.console.OmsConsoleLink

class OmsConsoleAction : BaseLinkAction() {

    override fun getLinkProject(myProject: IdeaProject.MyProject): BaseLink {
        return OmsConsoleLink.getInstance()
    }

}