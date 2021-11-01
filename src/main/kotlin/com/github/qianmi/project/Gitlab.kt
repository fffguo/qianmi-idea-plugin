package com.github.qianmi.project

import com.github.qianmi.config.GitlabConfig

private const val formatUrl = "{domain}/{group}/{projectName}.git"

interface Gitlab {

    /**
     * group
     */
    val gitlabGroup: String

    /**
     * 项目名称
     */
    val gitlabProjectName: String

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