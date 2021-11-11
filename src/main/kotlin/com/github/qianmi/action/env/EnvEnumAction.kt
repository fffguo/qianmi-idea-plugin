package com.github.qianmi.action.env

import cn.hutool.core.collection.CollectionUtil
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
        val myProject = AllProject.currentProject(e)

        myProject.env = env
        //bugatti
        myProject.bugatti.envCode = env.bugatti.envCode

        //shell
        myProject.shell.isSupport = CollectionUtil.isNotEmpty(myProject.shell.eleMap[env])

        //activeMq
        myProject.activeMq.isSupport = StringUtil.isNotBlank(env.activeMq.url)
        myProject.activeMq.url = env.activeMq.url

        //rocketMq
        myProject.rocketMq.isSupport = StringUtil.isNotBlank(env.rocketMq.url)
        myProject.rocketMq.url = env.rocketMq.url

        //dubboAdmin
        myProject.dubboAdmin.isSupport = StringUtil.isNotBlank(env.dubboAdmin.url)
        myProject.dubboAdmin.url = env.dubboAdmin.url

        //gavin
        myProject.gavin.isSupport = StringUtil.isNotBlank(env.gavin.url)
        myProject.gavin.url = env.gavin.url

        //trace
        myProject.trace.isSupport = StringUtil.isNotBlank(env.trace.url)
        myProject.trace.url = env.trace.url

        //qianmi admin
        myProject.qianmiAdmin.isSupport = StringUtil.isNotBlank(env.qianmiAdmin.url)
        myProject.qianmiAdmin.url = env.qianmiAdmin.url

        //console d2p mc
        myProject.consoleOfD2pMc.isSupport = StringUtil.isNotBlank(env.consoleOfD2pMc.url)
        myProject.consoleOfD2pMc.url = env.consoleOfD2pMc.url

        //console pc
        myProject.consoleOfPc.isSupport = StringUtil.isNotBlank(env.consoleOfPc.url)
        myProject.consoleOfPc.url = env.consoleOfPc.url

        //console oms
        myProject.consoleOfOms.isSupport = StringUtil.isNotBlank(env.consoleOfOms.url)
        myProject.consoleOfOms.url = env.consoleOfOms.url

    }

}