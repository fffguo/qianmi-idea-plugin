package com.github.qianmi.services

import com.github.qianmi.domain.enums.BugattiProjectEnum
import com.github.qianmi.domain.project.AllProject
import com.intellij.openapi.project.Project


class ProjectInitService(project: Project) {
    init {
        try {
            val myProject = AllProject.currentProject(project)

            val bugattiProjectEnum = BugattiProjectEnum.instanceOf(project.name)
            if (BugattiProjectEnum.NONE != bugattiProjectEnum) {
                myProject.bugatti.isSupport = true
                myProject.bugatti.projectCode = bugattiProjectEnum.bugattiProjectCode
            }
        } catch (e: Exception) {

        }
    }
}
