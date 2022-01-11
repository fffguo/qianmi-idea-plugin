package com.github.qianmi.util

import cn.hutool.json.JSONUtil


object JsonUtil {

    /**
     * 美观json
     */
    @JvmStatic
    fun prettyJson(jsonObj: Any): String {
        return JSONUtil.toJsonPrettyStr(jsonObj)
    }

    /**
     * 压缩json
     */
    @JvmStatic
    fun zipJson(jsonStr: String?): String? {
        if (JSONUtil.isJson(jsonStr)) {
            return JSONUtil.toJsonStr(JSONUtil.parse(jsonStr), 0)
        }
        return null
    }

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