package com.github.qianmi.action.env

import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.util.StringUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 更新环境
 */
class EnvEnumAction(var env: EnvEnum) : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        AllProject.currentProject(e).env = env
        //bugatti
        AllProject.currentProject(e).bugatti.envCode = env.bugatti.envCode

        //shell
        AllProject.currentProject(e).shell.isNeedSyncEle = true

        //activeMq
        AllProject.currentProject(e).activeMq.isSupport = StringUtil.isNotBlank(env.activeMq.url)
        AllProject.currentProject(e).activeMq.url = env.activeMq.url

        //rocketMq
        AllProject.currentProject(e).rocketMq.isSupport = StringUtil.isNotBlank(env.rocketMq.url)
        AllProject.currentProject(e).rocketMq.url = env.rocketMq.url

        //dubboAdmin
        AllProject.currentProject(e).dubboAdmin.isSupport = StringUtil.isNotBlank(env.dubboAdmin.url)
        AllProject.currentProject(e).dubboAdmin.url = env.dubboAdmin.url

        //gavin
        AllProject.currentProject(e).gavin.isSupport = StringUtil.isNotBlank(env.gavin.url)
        AllProject.currentProject(e).gavin.url = env.gavin.url

        //trace
        AllProject.currentProject(e).trace.isSupport = StringUtil.isNotBlank(env.trace.url)
        AllProject.currentProject(e).trace.url = env.trace.url

        //qianmi admin
        AllProject.currentProject(e).qianmiAdmin.isSupport = StringUtil.isNotBlank(env.qianmiAdmin.url)
        AllProject.currentProject(e).qianmiAdmin.url = env.qianmiAdmin.url

        //console d2p mc
        AllProject.currentProject(e).consoleOfD2pMc.isSupport = StringUtil.isNotBlank(env.consoleOfD2pMc.url)
        AllProject.currentProject(e).consoleOfD2pMc.url = env.consoleOfD2pMc.url

        //console pc
        AllProject.currentProject(e).consoleOfPc.isSupport = StringUtil.isNotBlank(env.consoleOfPc.url)
        AllProject.currentProject(e).consoleOfPc.url = env.consoleOfPc.url

        //console oms
        AllProject.currentProject(e).consoleOfOms.isSupport = StringUtil.isNotBlank(env.consoleOfOms.url)
        AllProject.currentProject(e).consoleOfOms.url = env.consoleOfOms.url

    }

}