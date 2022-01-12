package com.github.qianmi.infrastructure.util

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
}