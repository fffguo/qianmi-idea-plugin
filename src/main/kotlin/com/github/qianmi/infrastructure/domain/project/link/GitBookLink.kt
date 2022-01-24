package com.github.qianmi.infrastructure.domain.project.link

import com.intellij.openapi.project.Project

class GitBookLink(override var isSupport: Boolean) : BaseLink() {

    override fun getBrowserUrl(project: Project): String {
        return "http://gitbook.dev.qianmi.com/"
    }

    override fun getLinkName(): String {
        return "GitBook Link"
    }

    companion object {

        private val instance = GitBookLink(true)

        fun getInstance(): GitBookLink {
            return instance
        }
    }

}