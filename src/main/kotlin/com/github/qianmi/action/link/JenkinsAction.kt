package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.JenkinsLink
import com.github.qianmi.infrastructure.extend.StringExtend.isNotBlank
import com.intellij.openapi.actionSystem.AnActionEvent

class JenkinsAction : BaseLinkAction() {

    override fun getLinkProject(myProject: IdeaProject.MyProject): BaseLink {
        return JenkinsLink.getInstance()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = IdeaProject.getInstance(e).projectInfo.jenkins.isNotBlank()
    }
}