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
        //bugatti
        MyProject.bugatti.envCode = env.bugatti.envCode

        //shell
        MyProject.shell.isNeedSyncEle = true

        //activeMq
        MyProject.activeMq.isSupport = StringUtil.isNotBlank(env.activeMq.url)
        MyProject.activeMq.url = env.activeMq.url

        //rocketMq
        MyProject.rocketMq.isSupport = StringUtil.isNotBlank(env.rocketMq.url)
        MyProject.rocketMq.url = env.rocketMq.url

        //dubboAdmin
        MyProject.dubboAdmin.isSupport = StringUtil.isNotBlank(env.dubboAdmin.url)
        MyProject.dubboAdmin.url = env.dubboAdmin.url

        //gavin
        MyProject.gavin.isSupport = StringUtil.isNotBlank(env.gavin.url)
        MyProject.gavin.url = env.gavin.url

        //trace
        MyProject.trace.isSupport = StringUtil.isNotBlank(env.trace.url)
        MyProject.trace.url = env.trace.url

        //qianmi admin
        MyProject.qianmiAdmin.isSupport = StringUtil.isNotBlank(env.qianmiAdmin.url)
        MyProject.qianmiAdmin.url = env.qianmiAdmin.url

    }

}