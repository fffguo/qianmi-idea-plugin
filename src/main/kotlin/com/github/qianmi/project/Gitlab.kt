package com.github.qianmi.project

import com.github.qianmi.config.GitlabConfig

private const val formatUrl = "{domain}/{group}/{projectName}.git"

class Gitlab {

    /**
     * 是否支持
     */
    var isSupport: Boolean = false

    /**
     * group
     */
    var gitlabGroup: String = "gcs"

    /**
     * 项目名称
     */
    var gitlabProjectName: String = "purchase-wx-bff"

    constructor(isSupport: Boolean, gitlabGroup: String, gitlabProjectName: String) {
        this.isSupport = isSupport
        this.gitlabGroup = gitlabGroup
        this.gitlabProjectName = gitlabProjectName
    }

    constructor()

    /**
     * 获取bugatti url
     */
    fun getGitlabUrl(): String {
        return formatUrl
            .replace("{domain}", GitlabConfig.domain)
            .replace("{group}", gitlabGroup)
            .replace("{projectName}", gitlabProjectName)
    }

}