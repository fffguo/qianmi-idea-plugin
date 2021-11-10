package com.github.qianmi.action

import com.github.qianmi.domain.project.MyProject
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class GitBookAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(MyProject.gitBook.url)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = MyProject.gitBook.isSupport
    }

}