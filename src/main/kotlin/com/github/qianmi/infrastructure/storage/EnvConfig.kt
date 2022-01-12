package com.github.qianmi.infrastructure.storage

import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * 域账号配置
 */
@State(name = "qianmiEnvConfig", storages = [Storage("qianmiConfig.xml")])
class EnvConfig : PersistentStateComponent<EnvConfig> {

    var env: EnvEnum = EnvEnum.TEST1

    override fun getState(): EnvConfig {
        return this
    }

    override fun loadState(state: EnvConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): EnvConfig {
            return ApplicationManager.getApplication().getService(EnvConfig::class.java)
        }
    }
}


