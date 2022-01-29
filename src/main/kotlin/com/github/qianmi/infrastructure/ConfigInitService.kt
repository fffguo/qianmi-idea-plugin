package com.github.qianmi.infrastructure

import com.github.qianmi.infrastructure.domain.enums.BugattiProjectEnum
import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.util.BugattiHttpUtil
import com.intellij.openapi.project.Project


class ConfigInitService(var project: Project) {

    fun init() {

        val myProject = IdeaProject.getInstance(project)

        val bugattiProjectEnum = BugattiProjectEnum.instanceOf(myProject.name)
        if (BugattiProjectEnum.NONE == bugattiProjectEnum) {
            return
        }

        //登录失败直接返回
        val login = BugattiHttpUtil.login(project, false)
        if (!BugattiHttpUtil.isLoginSuccess(login)) {
            return
        }
        //获取项目信息
        myProject.projectInfo = BugattiHttpUtil.getProjectInfo(bugattiProjectEnum.code)

        myProject.bugattiLink.code = bugattiProjectEnum.code
        myProject.bugattiLink.name = bugattiProjectEnum.projectName
        myProject.bugattiLink.isSupport = true

    }

}
