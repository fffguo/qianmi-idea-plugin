package com.github.qianmi.infrastructure.domain.project

import com.github.qianmi.infrastructure.domain.project.link.BugattiLink
import com.github.qianmi.infrastructure.domain.project.tools.DubboAdminInvoke
import com.github.qianmi.infrastructure.domain.vo.BugattiProjectInfoResult
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

object IdeaProject {

    private var projectMap: HashMap<String, MyProject> = HashMap()

    fun getInstance(e: AnActionEvent): MyProject {
        return getInstance(e.project)
    }

    /**
     * 获取当前项目实例（idea多项目窗口）
     */
    fun getInstance(project: Project?): MyProject {
        if (project == null) {
            return getInstance("")
        }
        return getInstance(project.name)
    }

    @Synchronized
    private fun getInstance(projectName: String): MyProject {
        var myProject = projectMap[projectName]
        if (myProject == null) {
            myProject = MyProject(projectName)
            projectMap[projectName] = myProject
            return myProject
        }
        return myProject
    }


    class MyProject(
        /**
         * 项目名称
         */
        var name: String,
    ) {
        var bugattiLink: BugattiLink = BugattiLink.getInstance()
        var projectInfo: BugattiProjectInfoResult = BugattiProjectInfoResult()
        var dubboInvoke: DubboAdminInvoke = DubboAdminInvoke.defaultDubboAdminInvoke()

    }
}