package com.github.qianmi.infrastructure.domain.project.link


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