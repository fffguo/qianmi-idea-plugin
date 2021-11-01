package com.github.qianmi.project

import com.github.qianmi.config.BugattiConfig
import com.github.qianmi.enums.BugattiEnum

private const val formatUrl = "{domain}/#/list/{envCode}/info/{projectCode}?txt={projectName}"

interface Bugatti {

    /**
     * 布加迪项目code
     */
    val bugattiProjectCode: String

    /**
     * 布加迪项目名称
     */
    val bugattiProjectName: String


    /**
     * 获取bugatti url
     */
    fun getBugattiUrl(env: BugattiEnum): String {
        return formatUrl
            .replace("{domain}", BugattiConfig.domain)
            .replace("{envCode}", env.envCode)
            .replace("{projectCode}", bugattiProjectCode)
            .replace("{projectName}", bugattiProjectName)
    }

}