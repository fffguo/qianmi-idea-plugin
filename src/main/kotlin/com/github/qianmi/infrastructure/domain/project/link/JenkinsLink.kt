package com.github.qianmi.infrastructure.domain.project.link

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.intellij.openapi.project.Project

class JenkinsLink(override var isSupport: Boolean, var projectName: String) : BaseLink() {


    override fun getBrowserUrl(project: Project): String {
        val jenkinsName = IdeaProject.getInstance(project).projectInfo.jenkins
        return "http://jenkins.dev.qianmi.com/job/${jenkinsName}/"
    }

    override fun getLinkName(): String {
        return "Jenkin Link"
    }

    companion object {

        private val instance = JenkinsLink(false, "")

        fun getInstance(): JenkinsLink {
            return instance
        }
    }
}