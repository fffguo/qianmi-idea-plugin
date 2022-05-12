package com.github.qianmi.infrastructure.storage

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * 项目级配置
 */
@State(name = "qianmiProjectConfig", storages = [Storage("qianmiConfig.xml")])
class ProjectConfig : PersistentStateComponent<ProjectConfig> {
    //日志命令
    var logCommand: String = "tail -f -n 100 /home/tomcat/logs/springboot.out"


    override fun getState(): ProjectConfig {
        return this
    }

    override fun loadState(state: ProjectConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }
}


