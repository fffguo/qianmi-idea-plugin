package com.github.qianmi.services.vo

class BugattiLastVersionResult(

    /**
     * 分支名称
     */
    var branch: String,
    /**
     * 是否合并master
     */
    var mergeMaster: Boolean,
    /**
     * 发布版本号
     */
    var version: String,
    /**
     * 开发版本号
     */
    var sVersion: String,
) {

}