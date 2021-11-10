package com.github.qianmi.domain.project.link


class Gitlab(
    /**
     * 是否支持
     */
    var isSupport: Boolean,
    /**
     * gitLab 地址
     */
    var url: String,
) {
    companion object {
        fun defaultGitlab(): Gitlab {
            return Gitlab(false, "")
        }
    }
}