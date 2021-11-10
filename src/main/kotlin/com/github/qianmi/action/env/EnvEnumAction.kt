package com.github.qianmi.action.env

import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.MyProject
import com.github.qianmi.util.StringUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 更新环境
 */
class EnvEnumAction(var env: EnvEnum) : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        MyProject.env = env
        MyProject.dubboAdmin.url = env.dubboAdmin.url
        MyProject.rocketMq.url = env.rocketMq.url
        MyProject.activeMq.url = env.activeMq.url
        MyProject.bugatti.envCode = env.bugatti.envCode
        MyProject.shell.isNeedSyncEle = true

        if (StringUtil.isBlank(env.activeMq.url)) {
            MyProject.activeMq.isSupport = false
        }
        if (StringUtil.isBlank(env.dubboAdmin.url)) {
            MyProject.dubboAdmin.isSupport = false
        }
        if (StringUtil.isBlank(env.gavin.url)) {
            MyProject.gavin.isSupport = false
        }
        if (StringUtil.isBlank(env.rocketMq.url)) {
            MyProject.rocketMq.isSupport = false
        }
        if (StringUtil.isBlank(env.trace.url)) {
            MyProject.trace.isSupport = false
        }

    }

}