package com.github.qianmi.listeners

import com.github.qianmi.services.DefaultConfigInitService
import com.github.qianmi.services.ProjectInitService
import com.github.qianmi.services.ShellInitService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<ProjectInitService>()
        project.service<DefaultConfigInitService>()
        project.service<ShellInitService>()
    }
}
