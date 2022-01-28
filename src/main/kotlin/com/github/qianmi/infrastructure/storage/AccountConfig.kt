package com.github.qianmi.infrastructure.storage

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * 域账号配置
 */
@State(name = "qianmiDomainConfig", storages = [Storage("qianmiConfig.xml")])
class AccountConfig : PersistentStateComponent<AccountConfig> {

    var userName: String = ""
    var passwd: String = ""

    override fun getState(): AccountConfig {
        return this
    }

    override fun loadState(state: AccountConfig) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): AccountConfig {
            return ApplicationManager.getApplication().getService(AccountConfig::class.java)
        }
    }
}


