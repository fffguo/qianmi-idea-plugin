package com.github.qianmi.action

import com.github.qianmi.util.ProjectUtil
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class GitlabAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val gitlab = ProjectUtil.getProject(e).gitlab
        BrowserUtil.open(gitlab.getGitlabUrl())
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = ProjectUtil.getProject(e).gitlab.isSupport

    }


}