package com.github.qianmi.services

import cn.hutool.core.collection.CollectionUtil
import com.github.qianmi.domain.enums.BugattiProjectEnum
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.services.vo.BugattiProjectInfoResult
import com.github.qianmi.util.BugattiHttpUtil
import com.github.qianmi.util.StringUtil
import com.intellij.openapi.project.Project


class ConfigInitService(project: Project) {
    init {
        try {
            val myProject = AllProject.currentProject(project)

            //初始化布加迪属性
            initAttrWithBugatti(myProject)

            //获取项目信息
            val bugattiProjectInfo = BugattiHttpUtil.getProjectInfo(myProject)

            //更新gitlab属性
            updateAttrWithGitLab(myProject, bugattiProjectInfo)

            //更新bugatti属性
            updateAttrWithBugatti(myProject, bugattiProjectInfo)

            //同步shell节点
            syncShellElement(myProject)

        } catch (e: Exception) {

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

    private fun initAttrWithBugatti(myProject: AllProject.MyProject) {
        val bugattiProjectEnum = BugattiProjectEnum.instanceOf(myProject.name)
        if (BugattiProjectEnum.NONE != bugattiProjectEnum) {
            myProject.bugatti.isSupport = true
            myProject.bugatti.projectCode = bugattiProjectEnum.bugattiProjectCode
        }
    }

    companion object {
        fun syncShellElement(myProject: AllProject.MyProject) {

            val shellEleList = BugattiHttpUtil.getShellElementList(myProject)

            if (CollectionUtil.isNotEmpty(shellEleList)) {
                myProject.shell.isSupportShell = true
                myProject.shell.eleList = shellEleList
                myProject.shell.isNeedSyncEle = false
            }
        }
    }


}
