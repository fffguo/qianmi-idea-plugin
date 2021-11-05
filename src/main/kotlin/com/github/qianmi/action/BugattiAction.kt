package com.github.qianmi.action

import com.github.qianmi.config.EnvConfig
import com.github.qianmi.util.ProjectUtil
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class BugattiAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val bugatti = ProjectUtil.getProject(e).bugatti
        BrowserUtil.open(bugatti.getBugattiUrl(EnvConfig.getBugattiEnum()))
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = ProjectUtil.getProject(e).bugatti.isSupport
    }


}