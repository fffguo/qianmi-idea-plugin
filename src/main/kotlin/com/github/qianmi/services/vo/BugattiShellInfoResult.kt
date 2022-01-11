package com.github.qianmi.services.vo

class BugattiShellInfoResult {
    /**
     * 分组名称
     */
    lateinit var group: String

    /**
     * IP地址
     */
    lateinit var ip: String

    /**
     * 版本
     */
    lateinit var version: String

    constructor(group: String, ip: String, version: String) {
        this.group = group
        this.ip = ip
        this.version = version
    }
}