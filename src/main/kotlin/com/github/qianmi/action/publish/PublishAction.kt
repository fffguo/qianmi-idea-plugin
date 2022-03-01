package com.github.qianmi.action.publish

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.extend.StringExtend.isNotBlank
import com.github.qianmi.ui.publish.PublishPage
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 发布 action
 */
class PublishAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        PublishPage(e.project!!).open()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = IdeaProject.getInstance(e).projectInfo.projectId.isNotBlank()
    }
}