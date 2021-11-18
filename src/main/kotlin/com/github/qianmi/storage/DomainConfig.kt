package com.github.qianmi.storage

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * 域账号配置
 */
@State(name = "qianmiDomainConfig", storages = [Storage("qianmiConfig.xml")])
class DomainConfig : PersistentStateComponent<DomainConfig> {

    var userName: String = ""
    var passwd: String = ""

    override fun getState(): DomainConfig {
        return this
    }

    override fun loadState(state: DomainConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): DomainConfig {
            return ApplicationManager.getApplication().getService(DomainConfig::class.java)
        }
    }
}


