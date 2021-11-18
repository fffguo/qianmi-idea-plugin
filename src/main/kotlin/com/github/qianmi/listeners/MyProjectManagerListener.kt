package com.github.qianmi.listeners

import com.github.qianmi.services.ConfigInitService
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {

    private var log = logger<MyProjectManagerListener>()

    override fun projectOpened(project: Project) {
        try {
            project.getService(ConfigInitService::class.java).init()
        } catch (e: Exception) {
            log.error("初始化项目失败:", e)
        }

    }
}
