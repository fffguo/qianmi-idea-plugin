package com.github.qianmi.services

import cn.hutool.core.collection.CollectionUtil
import com.github.qianmi.domain.enums.BugattiProjectEnum
import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.services.vo.BugattiProjectInfoResult
import com.github.qianmi.util.BugattiHttpUtil
import com.github.qianmi.util.StringUtil
import com.intellij.openapi.project.Project


class ConfigInitService(var project: Project) {

    fun init() {

        val myProject = AllProject.currentProject(project)

        //初始化布加迪属性
        initAttrWithBugatti(myProject)

        //刷新cookie
        val refreshResult = BugattiHttpUtil.refreshCookie(project)
        if (!refreshResult) {
            return
        }

        //获取项目信息
        val bugattiProjectInfo = BugattiHttpUtil.getProjectInfo(myProject)

        //更新gitlab属性
        updateAttrWithGitLab(myProject, bugattiProjectInfo)

        //更新bugatti属性
        updateAttrWithBugatti(myProject, bugattiProjectInfo)

        //更新jenkins属性
        updateAttrWithJenkins(myProject, bugattiProjectInfo)

        //更新package属性
        updateAttrWithJPackage(myProject)

        //同步shell节点
        syncShellElement(myProject)
    }

    private fun syncShellElement(myProject: AllProject.MyProject) {

        //遍历处理环境上的shell节点
        for (envEnum in EnvEnum.values()) {
            val shellEleList = BugattiHttpUtil.getShellElementList(myProject.bugatti.projectCode, envEnum)
            myProject.shell.eleMap[envEnum] = shellEleList
        }
        //当前环境存在shell节点
        if (CollectionUtil.isNotEmpty(myProject.shell.eleMap[myProject.env])) {
            myProject.shell.isSupport = true
        }

    }

    private fun updateAttrWithGitLab(myProject: AllProject.MyProject, result: BugattiProjectInfoResult) {
        if (StringUtil.isNotBlank(result.git)) {
            myProject.gitlab.isSupport = true
            myProject.gitlab.url = result.git
        } else {
            myProject.gitlab.isSupport = false
        }
    }

    private fun updateAttrWithBugatti(myProject: AllProject.MyProject, result: BugattiProjectInfoResult) {
        myProject.bugatti.desc = result.description
        myProject.bugatti.projectName = result.projectName
        myProject.bugatti.isSupport = true
    }

    private fun updateAttrWithJenkins(myProject: AllProject.MyProject, result: BugattiProjectInfoResult) {
        myProject.jenkins.projectName = result.jenkins
        myProject.jenkins.isSupport = StringUtil.isNotBlank(result.jenkins)
    }

    private fun updateAttrWithJPackage(myProject: AllProject.MyProject) {
        myProject.jPackage.isSupport = true
    }

    private fun initAttrWithBugatti(myProject: AllProject.MyProject) {
        val bugattiProjectEnum = BugattiProjectEnum.instanceOf(myProject.name)
        if (BugattiProjectEnum.NONE != bugattiProjectEnum) {
            myProject.bugatti.isSupport = true
            myProject.bugatti.projectCode = bugattiProjectEnum.bugattiProjectCode
        }
    }

}
