package com.github.qianmi.infrastructure.domain.project.link

import com.intellij.openapi.project.Project

/**
 * mongoDB 管理台
 */
class MongoDBManagerLink(override var isSupport: Boolean) : BaseLink() {

    override fun getBrowserUrl(project: Project): String {
        return "http://mongo.dev.qianmi.com/"
    }

    override fun getLinkName(): String {
        return "MongoDB Manager Link"
    }


    companion object {

        private val instance = MongoDBManagerLink(true)

        fun getInstance(): MongoDBManagerLink {
            return instance
        }
    }
}