package com.github.qianmi.project

import com.github.qianmi.config.BugattiConfig
import com.github.qianmi.enums.BugattiEnum

private const val formatUrl = "{domain}/#/list/{envCode}/info/{projectCode}?txt={projectName}"

class Bugatti {

    /**
     * 是否支持
     */
    var isSupport: Boolean = false

    /**
     * 布加迪项目code
     */
    var bugattiProjectCode: String = "1403"

    /**
     * 布加迪项目名称
     */
    var bugattiProjectName: String = "purchase_wx_bff"


    constructor(isSupport: Boolean, bugattiProjectCode: String, bugattiProjectName: String) {
        this.isSupport = isSupport
        this.bugattiProjectCode = bugattiProjectCode
        this.bugattiProjectName = bugattiProjectName
    }

    constructor()

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