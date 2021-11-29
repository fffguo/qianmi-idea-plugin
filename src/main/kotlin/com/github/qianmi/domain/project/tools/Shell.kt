package com.github.qianmi.domain.project.tools

import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.AllProject
import com.github.qianmi.services.vo.BugattiShellInfoResult
import com.github.qianmi.storage.ShellConfig
import com.intellij.openapi.project.Project
import com.intellij.remote.AuthType
import com.intellij.ssh.config.unified.SshConfig
import com.intellij.ssh.config.unified.SshConfigManager
import com.intellij.ssh.ui.unified.SshUiData
import com.jetbrains.plugins.remotesdk.console.SshConsoleOptionsProvider
import com.jetbrains.plugins.remotesdk.console.SshTerminalCachingRunner
import com.jetbrains.plugins.webDeployment.config.FileTransferConfig
import com.jetbrains.plugins.webDeployment.config.GroupedServersConfigManager
import com.jetbrains.plugins.webDeployment.config.PublishConfig
import com.jetbrains.plugins.webDeployment.config.WebServerConfig
import com.jetbrains.plugins.webDeployment.ui.WebServerToolWindowFactory
import com.jetbrains.plugins.webDeployment.ui.WebServerToolWindowPanel
import org.jetbrains.plugins.terminal.TerminalTabState
import org.jetbrains.plugins.terminal.TerminalView
import java.nio.charset.Charset

class Shell(

    /**
     * 是否支持shell
     */
    var isSupport: Boolean,

    /**
     * 节点信息
     */
    var eleMap: HashMap<EnvEnum, List<Element>>,

    ) {
    companion object {
        fun defaultShell(): Shell {
            return Shell(isSupport = false, eleMap = hashMapOf(AllProject.defaultEnv to listOf()))
        }
    }


    fun getShellCharset(): Charset {
        return Charset.forName("utf-8")
    }

    class Element {
        /**
         * ip 地址
         */
        var ip: String = ""

        /**
         * 用户名
         */
        var userName: String = ShellConfig.getInstance().userName

        /**
         * 密码
         */
        var password: String = ShellConfig.getInstance().passwd

        /**
         * 端口
         */
        var port: Int = ShellConfig.getInstance().port

        /**
         * 工作路径
         */
        var workingDirectory: String = ShellConfig.getInstance().dir

        /**
         * 分组名称
         */
        var group: String = "default"

        /**
         * 版本
         */
        var version: String = ""

        companion object {
            fun instanceOf(result: BugattiShellInfoResult): Element {
                val element = Element()
                element.ip = result.ip
                element.group = result.group
                element.version = result.version
                return element
            }
        }

        fun openSshTerminal(project: Project) {
            val charset = SshConsoleOptionsProvider.getInstance(project).charset
            val runner = SshTerminalCachingRunner(project, createSshUiData(), charset)
            //开启openssh
            TerminalView.getInstance(project).createNewSession(runner, buildTerminalTabState())
        }


        fun openSftp(project: Project) {
            val ftpName = "${project.name}: $group"
            //添加服务
            val serversConfigManager = GroupedServersConfigManager.getInstance(project)
            if (serversConfigManager.findServer(ip) == null) {
                serversConfigManager.addServer(createWebServerConfig(project, ftpName))
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
            ftpPanel.selectInServerByName(project, ftpName, WebServerConfig.RemotePath(workingDirectory))
            val declaredMethod = WebServerToolWindowPanel::class.java.getDeclaredMethod("constructTree")
            declaredMethod.isAccessible = true
            declaredMethod.invoke(ftpPanel)
            //show
            toolWindow.show()
        }

        private fun buildTerminalTabState(): TerminalTabState {
            val state = TerminalTabState()
            state.myTabName = group + "：" + this.ip
            state.myWorkingDirectory = this.workingDirectory
            return state
        }

        private fun createSshUiData(): SshUiData {
            val password = SshConfig.AuthData.create(password, null, true, false)
            val sshConfig = SshConfig.create(
                true,
                ip,
                port,
                userName,
                AuthType.PASSWORD,
                null
            )
            sshConfig.saveAuthDataToPasswordSafe(password)
            return SshUiData(sshConfig)
        }

        private fun createFileTransferConfig(project: Project): FileTransferConfig {
            //注册ssh配置
            val sshConfigManager = SshConfigManager.getInstance(project)
            val sshUiData = sshConfigManager.register(createSshUiData())

            val fileTransferConfig = FileTransferConfig()
            fileTransferConfig.setSshConfig(sshUiData.config)
            fileTransferConfig.rootFolder = workingDirectory
            return fileTransferConfig
        }

        private fun createWebServerConfig(project: Project, ftpName: String): WebServerConfig {
            val webServerConfig = WebServerConfig()
            webServerConfig.id = ip
            webServerConfig.setName(ftpName)
            webServerConfig.setIsProjectLevel(true)
            webServerConfig.fileTransferConfig = createFileTransferConfig(project)
            return webServerConfig
        }

    }

}