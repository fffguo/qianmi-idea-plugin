package com.github.qianmi.storage

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * shell配置
 */
@State(name = "qianmiShellConfig", storages = [Storage("qianmiConfig.xml")])
class ShellConfig : PersistentStateComponent<ShellConfig> {

    var userName: String = "root"
    var passwd: String = ""
    var port: Int = 22
    var jvmPort: Int = 8000
    var dir: String = "/home/tomcat"


    override fun getState(): ShellConfig {
        return this
    }

    override fun loadState(state: ShellConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): ShellConfig {
            return ApplicationManager.getApplication().getService(ShellConfig::class.java)
        }
    }
}


