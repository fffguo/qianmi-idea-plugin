package com.github.qianmi.action.env

import com.github.qianmi.config.EnvConfig
import com.github.qianmi.enums.EnvEnum
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.ex.ComboBoxAction
import com.intellij.util.containers.stream
import java.util.stream.Collectors
import javax.swing.JComponent

/**
 * 千米环境切换
 */
class EnvAction : ComboBoxAction() {

    override fun createPopupActionGroup(button: JComponent?): DefaultActionGroup {
        val actions = EnvEnum.values()
            .stream()
            .map { env -> EnvEnumAction(env) }
            .peek { envAction ->
                envAction.templatePresentation.text = envAction.env.code
            }
            .collect(Collectors.toList())

        return DefaultActionGroup(actions)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.text = EnvConfig.getCurrentEnv().code
    }
}