package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.github.qianmi.infrastructure.domain.project.link.GitlabLink
import com.github.qianmi.infrastructure.extend.StringExtend.isNotBlank
import com.intellij.openapi.actionSystem.AnActionEvent

class GitlabAction : BaseLinkAction() {

    override fun getLinkProject(myProject: IdeaProject.MyProject): BaseLink {
        return GitlabLink.getInstance()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = IdeaProject.getInstance(e).projectInfo.git.isNotBlank()
    }
}