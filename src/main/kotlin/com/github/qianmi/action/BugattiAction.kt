package com.github.qianmi.action

import com.github.qianmi.domain.project.MyProject
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class BugattiAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(MyProject.bugatti.getBugattiUrl())
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = MyProject.bugatti.isSupport
    }


}