package com.github.qianmi.action

import com.github.qianmi.domain.project.MyProject
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class GitlabAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(MyProject.gitlab.url)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = MyProject.gitlab.isSupport
    }


}