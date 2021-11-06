package com.github.qianmi.project

private const val formatUrl = "{domain}/{group}/{projectName}.git"

class Gitlab {

    /**
     * 是否支持
     */
    var isSupport: Boolean

    /**
     * gitLab 地址
     */
    var url: String

    constructor(isSupport: Boolean, url: String) {
        this.isSupport = isSupport
        this.url = url
    }

    companion object {
        fun defaultGitlab(): Gitlab {
            return Gitlab(false, "")
        }
    }
}