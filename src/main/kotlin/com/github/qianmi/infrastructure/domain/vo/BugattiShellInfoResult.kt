package com.github.qianmi.infrastructure.domain.vo

class BugattiShellInfoResult {

    /**
     * hostId
     */
    var id: String

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

    /**
     * 标签
     */
    var tag: String

    constructor(id: String, group: String, ip: String, version: String, tag: String) {
        this.id = id
        this.group = group
        this.ip = ip
        this.version = version
        this.tag = tag
    }
}