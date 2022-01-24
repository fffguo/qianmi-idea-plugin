package com.github.qianmi.infrastructure.util

import cn.hutool.json.JSONUtil
import com.github.qianmi.infrastructure.extend.StringExtend.isBlank


object JsonUtil {

    /**
     * 美观json
     */
    @JvmStatic
    fun prettyJson(jsonObj: Any): String {
        if (jsonObj is String && jsonObj.isBlank()) {
            return ""
        }
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