package com.github.qianmi.action.packaging

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.ui.packages.PackagePage
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 打包 action
 */
class PackageAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        PackagePage(e.project!!).open()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = IdeaProject.getInstance(e).projectInfo.jenkins.isNotBlank()
    }
}