package com.github.qianmi.action

import com.github.qianmi.ui.setting.PluginSetting
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.options.ShowSettingsUtil

/**
 * 打开设置对话框
 */
class SettingAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        ApplicationManager.getApplication().invokeLater({
            ShowSettingsUtil.getInstance().showSettingsDialog(e.project, PluginSetting::class.java)
        }, ModalityState.defaultModalityState())
    }

    override fun update(e: AnActionEvent) {
    }

}