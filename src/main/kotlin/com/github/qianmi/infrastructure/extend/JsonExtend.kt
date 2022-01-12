package com.github.qianmi.infrastructure.extend

import cn.hutool.json.JSONUtil

object JsonExtend {

    fun String.isJsonString(): Boolean {
        return JSONUtil.isJson(this)
    }

    fun Any.toJsonString(): String {
        return JSONUtil.toJsonStr(this)
    }

    inline fun <reified T> String.toBean(): T? {
        return JSONUtil.toBean(this, T::class.java)
    }

    inline fun <reified T> String.toList(): List<T> {
        return JSONUtil.toList(this, T::class.java)
    }
}