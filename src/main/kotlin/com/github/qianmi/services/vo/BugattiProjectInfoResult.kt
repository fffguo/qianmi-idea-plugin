package com.github.qianmi.services.vo

class BugattiProjectInfoResult {
    /**
     * 项目ID
     */
    var projectId: String

    /**
     * 布加迪项目名称
     */
    var projectName: String

    /**
     * git 地址
     */
    var git: String

    /**
     * jenkins 项目名称
     */
    var jenkins: String

    /**
     * 项目描述
     */
    var description: String

    constructor(projectId: String, projectName: String, git: String, jenkins: String, description: String) {
        this.projectId = projectId
        this.projectName = projectName
        this.git = git
        this.jenkins = jenkins
        this.description = description
    }


}