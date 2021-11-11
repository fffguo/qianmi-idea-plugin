package com.github.qianmi.listeners

import com.github.qianmi.services.ConfigInitService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<ConfigInitService>()
    }
}
