package com.github.qianmi.infrastructure.domain.project.link

import com.intellij.openapi.project.Project

class WikiLink(override var isSupport: Boolean) : BaseLink() {


    override fun getBrowserUrl(project: Project): String {
        return "http://wiki.dev.qianmi.com/"
    }

    override fun getLinkName(): String {
        return "Wiki Link"
    }

    companion object {

        private val instance = WikiLink(true)

        fun getInstance(): WikiLink {
            return WikiLink(true)
        }
    }
}