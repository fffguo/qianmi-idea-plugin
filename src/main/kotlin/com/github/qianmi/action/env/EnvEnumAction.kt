package com.github.qianmi.action.env

import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.extend.CollectionExtend.isNotEmpty
import com.github.qianmi.infrastructure.extend.StringExtend.isNotBlank
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 更新环境
 */
class EnvEnumAction(var env: EnvEnum) : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val myProject = com.github.qianmi.infrastructure.domain.project.AllProject.currentProject(e)

        EnvConfig.getInstance().env = env

        //bugatti
        myProject.bugatti.envCode = env.bugatti.envCode

        //shell
        myProject.shell.isSupport = myProject.shell.eleMap[env].isNotEmpty()

        //activeMq
        myProject.activeMq.isSupport = env.activeMq.url.isNotBlank()
        myProject.activeMq.url = env.activeMq.url

        //rocketMq
        myProject.rocketMq.isSupport = env.rocketMq.url.isNotBlank()
        myProject.rocketMq.url = env.rocketMq.url

        //dubboAdmin
        myProject.dubboAdmin.isSupport = env.dubboAdmin.url.isNotBlank()
        myProject.dubboAdmin.url = env.dubboAdmin.url

        //gavin
        myProject.gavin.isSupport = env.gavin.url.isNotBlank()
        myProject.gavin.url = env.gavin.url

        //trace
        myProject.trace.isSupport = env.trace.url.isNotBlank()
        myProject.trace.url = env.trace.url

        //qianmi admin
        myProject.qianmiAdmin.isSupport = env.qianmiAdmin.url.isNotBlank()
        myProject.qianmiAdmin.url = env.qianmiAdmin.url

        //console d2p mc
        myProject.consoleOfD2pMc.isSupport = env.consoleOfD2pMc.url.isNotBlank()
        myProject.consoleOfD2pMc.url = env.consoleOfD2pMc.url

        //console pc
        myProject.consoleOfPc.isSupport = env.consoleOfPc.url.isNotBlank()
        myProject.consoleOfPc.url = env.consoleOfPc.url

        //console oms
        myProject.consoleOfOms.isSupport = env.consoleOfOms.url.isNotBlank()
        myProject.consoleOfOms.url = env.consoleOfOms.url

    }

}