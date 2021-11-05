package com.github.qianmi.action.env

import com.github.qianmi.config.EnvConfig
import com.github.qianmi.enums.EnvEnum
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 更新环境
 */
class EnvEnumAction(var env: EnvEnum) : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        EnvConfig.setCurrentEnv(env)
    }

}