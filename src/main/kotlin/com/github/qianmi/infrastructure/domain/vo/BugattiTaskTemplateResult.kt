package com.github.qianmi.infrastructure.domain.vo

class BugattiTaskTemplateResult {

    //名称：安装应用
    lateinit var name: String

    //ID
    lateinit var id: String

    //deploy：安装应用
    //start：启动应用
    lateinit var actionFlag: String

    fun isInstall(): Boolean {
        return actionFlag == "deploy"
    }

    fun isStart(): Boolean {
        return actionFlag == "start"
    }

}