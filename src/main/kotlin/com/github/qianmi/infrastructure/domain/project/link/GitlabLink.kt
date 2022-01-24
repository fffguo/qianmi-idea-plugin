package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.intellij.openapi.project.Project


class GitlabLink(override var isSupport: Boolean) : BaseLink() {

    override fun getBrowserUrl(project: Project): String {
        return IdeaProject.getInstance(project).projectInfo.git
    }

    override fun getLinkName(): String {
        return "GitLab Link"
    }


    companion object {

        private val instance = GitlabLink(true)

        fun getInstance(): GitlabLink {
            return instance
        }
    }

}