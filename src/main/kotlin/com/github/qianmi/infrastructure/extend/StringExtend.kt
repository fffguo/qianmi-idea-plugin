package com.github.qianmi.infrastructure.extend

import cn.hutool.core.util.StrUtil

object StringExtend {

    fun CharSequence.isNotBlank(): Boolean {
        return StrUtil.isNotBlank(this)
    }

    fun CharSequence.isBlank(): Boolean {
        return StrUtil.isBlank(this)
    }
}