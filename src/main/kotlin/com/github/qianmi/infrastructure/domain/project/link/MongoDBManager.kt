package com.github.qianmi.infrastructure.domain.project.link

/**
 * mongoDB 管理台
 */
class MongoDBManager(
    /**
     * 是否支持
     */
    var isSupport: Boolean,

    /**
     * 地址
     */
    var url: String,
) {
    companion object {
        fun defaultMongoDBManager(): MongoDBManager {
            return MongoDBManager(true, "http://mongo.dev.qianmi.com/")
        }
    }
}