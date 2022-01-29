package com.github.qianmi.infrastructure.domain.vo

class BugattiProjectInfoResult {

    /**
     * 项目ID
     */
    lateinit var projectId: String

    /**
     * 布加迪项目名称
     */
    lateinit var projectName: String

    /**
     * git 地址
     */
    lateinit var git: String

    /**
     * jenkins 项目名称
     */
    lateinit var jenkins: String

    /**
     * 项目描述
     */
    lateinit var description: String

    /**
     * 模板ID
     */
    lateinit var templateId: String

}