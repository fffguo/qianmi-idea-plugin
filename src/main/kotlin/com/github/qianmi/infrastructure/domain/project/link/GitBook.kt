package com.github.qianmi.infrastructure.domain.project.link

class GitBook(
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
        fun defaultGitBook(): GitBook {
            return GitBook(true, "http://gitbook.dev.qianmi.com/")
        }
    }
}