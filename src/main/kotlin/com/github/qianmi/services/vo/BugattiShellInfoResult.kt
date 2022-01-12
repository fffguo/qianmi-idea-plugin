package com.github.qianmi.services.vo

class BugattiShellInfoResult {
    /**
     * 分组名称
     */
    var group: String

    /**
     * IP地址
     */
    var ip: String

    /**
     * 版本
     */
    var version: String

    constructor(group: String, ip: String, version: String) {
        this.group = group
        this.ip = ip
        this.version = version
    }
}