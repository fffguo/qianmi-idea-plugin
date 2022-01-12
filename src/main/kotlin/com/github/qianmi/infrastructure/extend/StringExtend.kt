package com.github.qianmi.infrastructure.extend

import cn.hutool.core.util.StrUtil

object StringExtend {

    fun String.isNotBlank(): Boolean {
        return StrUtil.isNotBlank(this)
    }
}