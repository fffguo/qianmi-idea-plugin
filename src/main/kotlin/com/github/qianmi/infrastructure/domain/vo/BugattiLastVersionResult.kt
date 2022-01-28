package com.github.qianmi.infrastructure.domain.vo

class BugattiLastVersionResult {
    /**
     * 分支名称
     */
    lateinit var branch: String

    /**
     * 是否合并master
     */
    var mergeMaster: Boolean = false

    /**
     * 发布版本号
     */
    lateinit var version: String

    /**
     * 开发版本号
     */
    lateinit var sVersion: String
}