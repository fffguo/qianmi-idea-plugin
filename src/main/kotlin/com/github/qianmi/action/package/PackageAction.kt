package com.github.qianmi.action.`package`

import com.github.qianmi.domain.project.AllProject
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 打包 action
 */
class PackageAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        PackageDialog(e.project!!).show()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = AllProject.currentProject(e).jPackage.isSupport
    }
}