package com.github.qianmi.action.env

import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.MyProject
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 更新环境
 */
class EnvEnumAction(var env: EnvEnum) : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        MyProject.env = env
        MyProject.dubboAdmin.domain = env.dubboAdmin.domain
        MyProject.bugatti.envCode = env.bugatti.envCode
        MyProject.shell.isNeedSyncEle = true
    }

}