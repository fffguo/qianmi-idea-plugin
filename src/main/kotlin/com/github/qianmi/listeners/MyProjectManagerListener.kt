package com.github.qianmi.listeners

import com.github.qianmi.infrastructure.storage.TempConfig
import com.github.qianmi.services.ConfigInitService
import com.intellij.execution.RunManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.jetbrains.plugins.webDeployment.config.GroupedServersConfigManager
import com.jetbrains.plugins.webDeployment.config.PublishConfig

internal class MyProjectManagerListener : ProjectManagerListener {

    private var log = logger<MyProjectManagerListener>()

    override fun projectOpened(project: Project) {
        try {
            project.getService(ConfigInitService::class.java).init()
        } catch (e: Exception) {
            log.error("初始化项目失败:", e)
        }

    }

    override fun projectClosingBeforeSave(project: Project) {
        try {
            val tempConfig = TempConfig.getInstance(project)
            clearFtpConfig(project, tempConfig)
            clearRemoteJvmDebugConfig(project, tempConfig)

        } catch (e: Exception) {
            log.error("项目关闭，清除临时配置失败:", e)
        }
    }

    private fun clearFtpConfig(project: Project, tempConfig: TempConfig) {
        val serversConfigManager = GroupedServersConfigManager.getInstance(project)
        tempConfig.ftpConfigList.forEach { config ->
            run {
                serversConfigManager.removeServer(config.id)
            }

        }
        project.getService(PublishConfig::class.java).setDefaultGroupOrServerName("")
        tempConfig.ftpConfigList = mutableListOf()
    }

    private fun clearRemoteJvmDebugConfig(project: Project, tempConfig: TempConfig) {
        tempConfig.remoteJvmDebugConfigList.forEach { config ->
            run {
                RunManager.getInstance(project).removeConfiguration(config)
            }
        }
        tempConfig.remoteJvmDebugConfigList = mutableListOf()
    }
}
