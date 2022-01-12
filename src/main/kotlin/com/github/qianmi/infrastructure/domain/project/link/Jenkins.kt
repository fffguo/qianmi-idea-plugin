package com.github.qianmi.infrastructure.domain.project.link

class Jenkins(
    /**
     * 是否支持
     */
    var isSupport: Boolean,

    /**
     * jenkins项目名
     */
    var projectName: String,

    ) {
    private val formatUrl = "http://jenkins.dev.qianmi.com/job/{projectName}/"

    companion object {
        fun defaultJenkins(): Jenkins {
            return Jenkins(false, "")
        }
    }

    /**
     * 获取 jenkins url
     */
    fun getJenkinsUrl(): String {
        return formatUrl
            .replace("{projectName}", projectName)
    }
}