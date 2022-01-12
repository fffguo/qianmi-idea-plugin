package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.AllProject
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class BugattiAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(AllProject.currentProject(e).bugatti.getBugattiUrl())
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = AllProject.currentProject(e).bugatti.isSupport
    }

    companion object {
        fun instanceOf(presentText: String): BugattiAction {
            val bugattiAction = BugattiAction()
            bugattiAction.templatePresentation.text = presentText
            return bugattiAction
        }
    }

}