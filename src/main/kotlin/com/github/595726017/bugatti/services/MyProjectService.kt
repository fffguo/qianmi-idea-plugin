package com.github.595726017.bugatti.services

import com.intellij.openapi.project.Project
import com.github.595726017.bugatti.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
