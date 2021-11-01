package com.github.qianmi.action

import com.github.qianmi.enums.EnvEnum
import com.github.qianmi.util.ProjectUtil
import com.github.qianmi.util.QianmiIconUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.guessProjectDir
import com.intellij.remoteServer.configuration.RemoteServersManager
import com.intellij.sh.run.ShRunner


class ShellAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = ProjectUtil.getProject(e)!!

        val shRunner = ServiceManager.getService(ShRunner::class.java)
        val command = project.getShellCommand()
        val workingDirectory = e.project!!.guessProjectDir()!!.path
        val title = EnvEnum.TEST2.desc + ":" + project.shellIp

        RemoteServersManager.getInstance().run {
            println("hello")
        }
//        RemoteServersManager.getInstance().addServer()
        shRunner.run(e.project!!, command, workingDirectory, title, true)
        RemoteServersManager.getInstance().addServer().run {  }
    }


    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = ProjectUtil.getProject(e) != null
        e.presentation.icon = QianmiIconUtil.shell

    }

}