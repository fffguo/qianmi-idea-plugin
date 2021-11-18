package com.github.qianmi.action

import com.github.qianmi.ui.PluginSettingPage
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.options.ShowSettingsUtil

class AccountAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        ApplicationManager.getApplication().invokeLater({
            ShowSettingsUtil.getInstance().showSettingsDialog(e.project, PluginSettingPage::class.java)
        }, ModalityState.defaultModalityState())
    }

    override fun update(e: AnActionEvent) {
    }

}