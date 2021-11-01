package com.github.qianmi.util

import com.github.qianmi.project.list.PlugDemo
import com.github.qianmi.project.list.PurchaseWxBff
import com.intellij.openapi.actionSystem.AnActionEvent

object ProjectUtil {

    private val projectMap = hashMapOf(
        PurchaseWxBff.name to PurchaseWxBff,
        PlugDemo.name to PlugDemo
    )

    @JvmStatic
    fun getProject(e: AnActionEvent) = projectMap[getProjectName(e)]

    @JvmStatic
    fun getProjectName(e: AnActionEvent) = e.project?.name

}