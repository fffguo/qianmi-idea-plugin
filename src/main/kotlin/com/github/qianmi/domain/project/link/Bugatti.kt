package com.github.qianmi.domain.project.link

import com.github.qianmi.config.BugattiConfig


class Bugatti(
    /**
     * 是否支持
     */
    var isSupport: Boolean,
    /**
     * 布加迪项目code
     */
    var projectCode: String,
    /**
     * 布加迪项目名称
     */
    var projectName: String,
    /**
     * 布加迪环境 code
     */
    var envCode: String,
    /**
     * 项目描述
     */
    var desc: String,

    ) {


    companion object {

        private const val formatUrl = "{domain}/#/list/{envCode}/info/{projectCode}?txt={projectName}"

        fun defaultBugatti(): Bugatti {
            return Bugatti(false, "", "", "", "")
        }
    }

    /**
     * 获取bugatti url
     */
    fun getBugattiUrl(): String {
        return formatUrl
            .replace("{domain}", BugattiConfig.domain)
            .replace("{envCode}", envCode)
            .replace("{projectCode}", projectCode)
            .replace("{projectName}", projectName)
    }

}