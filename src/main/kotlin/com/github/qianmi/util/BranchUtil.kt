package com.github.qianmi.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.ui.CurrentBranchComponent

object BranchUtil {

    @JvmStatic
    fun getCurrentBranchName(project: Project): String {
        val currentBranch = CurrentBranchComponent.getCurrentBranch(project, project.projectFile!!) ?: return ""
        return currentBranch.branchName ?: ""
    }
}