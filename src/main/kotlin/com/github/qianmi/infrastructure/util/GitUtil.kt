package com.github.qianmi.infrastructure.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.ui.CurrentBranchComponent

object GitUtil {

    @JvmStatic
    fun getCurrentBranchName(project: Project): String {
        val currentBranch = CurrentBranchComponent.getCurrentBranch(project, project.projectFile!!) ?: return ""
        return currentBranch.branchName ?: ""
    }
}