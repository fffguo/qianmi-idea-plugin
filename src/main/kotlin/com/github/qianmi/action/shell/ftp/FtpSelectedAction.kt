package com.github.qianmi.action.shell.ftp

import com.github.qianmi.action.shell.ShellSelectedAction
import com.github.qianmi.infrastructure.domain.project.tools.ShellElement
import com.github.qianmi.infrastructure.storage.EnvConfig
import com.github.qianmi.infrastructure.storage.TempConfig
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.ssh.config.unified.SshConfigManager
import com.jetbrains.plugins.webDeployment.config.FileTransferConfig
import com.jetbrains.plugins.webDeployment.config.GroupedServersConfigManager
import com.jetbrains.plugins.webDeployment.config.PublishConfig
import com.jetbrains.plugins.webDeployment.config.WebServerConfig
import com.jetbrains.plugins.webDeployment.ui.WebServerToolWindowFactory
import com.jetbrains.plugins.webDeployment.ui.WebServerToolWindowPanel


class FtpSelectedAction(override var ele: ShellElement) : ShellSelectedAction() {

    override fun open(e: AnActionEvent) {
        val project = e.project!!

        val ftpName = "${project.name}: ${EnvConfig.getInstance().env.envName}(${ele.group})"
        //添加服务
        val serversConfigManager = GroupedServersConfigManager.getInstance(project)
        if (serversConfigManager.findServer(ele.ip) == null) {
            val config = createWebServerConfig(project, ftpName)
            serversConfigManager.addServer(config)
            TempConfig.getInstance(project).ftpConfigList.add(config)
        }
        //显示
        showFtpWindow(project, ftpName)
    }

    private fun showFtpWindow(project: Project, ftpName: String) {
        //默认选中
        val toolWindow = WebServerToolWindowFactory.getWebServerToolWindow(project)
        //加粗
        project.getService(PublishConfig::class.java).setDefaultGroupOrServerName(ftpName)
        //反射选中，重刷tree
        val ftpPanel = toolWindow.contentManager.contents[0].component as WebServerToolWindowPanel
        ftpPanel.selectInServerByName(project, ftpName, WebServerConfig.RemotePath(ele.getWorkingDirectory()))
        val declaredMethod = WebServerToolWindowPanel::class.java.getDeclaredMethod("constructTree")
        declaredMethod.isAccessible = true
        declaredMethod.invoke(ftpPanel)
        //show
        toolWindow.show()
    }


    private fun createWebServerConfig(project: Project, ftpName: String): WebServerConfig {
        val webServerConfig = WebServerConfig()
        webServerConfig.id = ele.ip
        webServerConfig.setName(ftpName)
        webServerConfig.setIsProjectLevel(true)
        webServerConfig.fileTransferConfig = createFileTransferConfig(project)
        return webServerConfig
    }

    private fun createFileTransferConfig(project: Project): FileTransferConfig {
        //注册ssh配置
        val sshConfigManager = SshConfigManager.getInstance(project)
        val sshUiData = sshConfigManager.register(ele.createSshUiData())

        val fileTransferConfig = FileTransferConfig()
        fileTransferConfig.setSshConfig(sshUiData.config)
        fileTransferConfig.rootFolder = ele.getWorkingDirectory()
        return fileTransferConfig
    }

}