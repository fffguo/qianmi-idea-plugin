package com.github.qianmi.util

import com.github.qianmi.project.QianmiProject
import com.github.qianmi.project.list.PlugDemo
import com.github.qianmi.project.list.PurchaseWxBff
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

object ProjectUtil {

    private val projectMap = hashMapOf(
        PurchaseWxBff.name to PurchaseWxBff,
        PlugDemo.name to PlugDemo
    )

    @JvmStatic
    fun getProject(e: AnActionEvent): QianmiProject {
        return projectMap[getProjectName(e)] ?: throw RuntimeException("当前项目不支持千米插件")
    }

    @JvmStatic
    fun getProject(project: Project) = projectMap[project.name]

    @JvmStatic
    fun getProjectName(e: AnActionEvent) = e.project?.name

}