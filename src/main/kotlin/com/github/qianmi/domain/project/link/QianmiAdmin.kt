package com.github.qianmi.domain.project.link

import com.github.qianmi.domain.project.MyProject

class QianmiAdmin(
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
        fun defaultQianmiAdmin(): QianmiAdmin {
            return QianmiAdmin(true, MyProject.env.qianmiAdmin.url)
        }
    }
}