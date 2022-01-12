package com.github.qianmi.infrastructure.storage

import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl
import com.intellij.openapi.project.Project
import com.jetbrains.plugins.webDeployment.config.WebServerConfig

/**
 * 临时配置，项目关闭要清除配置
 */
class TempConfig {

    /**
     * ftp配置
     */
    var ftpConfigList: MutableList<WebServerConfig> = mutableListOf()

    /**
     * jvm debug配置
     */
    var remoteJvmDebugConfigList: MutableList<RunnerAndConfigurationSettingsImpl> = mutableListOf()


    companion object {
        fun getInstance(project: Project): TempConfig {
            return project.getService(TempConfig::class.java)
        }
    }
}


