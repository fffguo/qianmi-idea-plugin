package com.github.qianmi.infrastructure.storage

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * cookie
 */
@State(name = "qianmiBugattiCookie", storages = [Storage("qianmiConfig.xml")])
class BugattiCookie : PersistentStateComponent<BugattiCookie> {

    var cookie: String = ""

    override fun getState(): BugattiCookie {
        return this
    }

    override fun loadState(state: BugattiCookie) {
        XmlSerializerUtil.copyBean(state, this)
    }


    companion object {
        fun getInstance(): BugattiCookie {
            return ApplicationManager.getApplication().getService(BugattiCookie::class.java)
        }
    }
}


