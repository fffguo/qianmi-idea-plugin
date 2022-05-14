package com.github.qianmi.infrastructure.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

object JMessageUtil {

    @JvmStatic
    fun showError(project: Project?, title: String, message: String) {
        Messages.showErrorDialog(project, message, title)
    }

    @JvmStatic
    fun showTrue(project: Project?, title: String, message: String) {
        Messages.showInfoMessage(project, message, title)
    }
}