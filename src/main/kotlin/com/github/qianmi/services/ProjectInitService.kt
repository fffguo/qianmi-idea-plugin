package com.github.qianmi.services

import com.github.qianmi.domain.enums.BugattiProjectEnum
import com.github.qianmi.domain.project.MyProject
import com.intellij.openapi.project.Project


class ProjectInitService(project: Project) {
    init {
        MyProject.name = project.name

        val bugattiProjectEnum = BugattiProjectEnum.instanceOf(project.name)
        if (BugattiProjectEnum.NONE != bugattiProjectEnum) {
            MyProject.bugatti.isSupport = true
            MyProject.bugatti.projectCode = bugattiProjectEnum.bugattiProjectCode
        }
        MyProject.dubboAdmin.isSupport = true
    }
}
