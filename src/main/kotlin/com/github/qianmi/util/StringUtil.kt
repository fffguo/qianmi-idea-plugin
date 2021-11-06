package com.github.qianmi.util

import org.apache.commons.lang3.StringUtils

object StringUtil {

    @JvmStatic
    fun isNotBlank(cs: CharSequence): Boolean {
        return !isBlank(cs)
    }

    @JvmStatic
    fun isBlank(cs: CharSequence): Boolean {
        val strLen = StringUtils.length(cs)
        if (strLen == 0) {
            return true
        }
        for (i in 0 until strLen) {
            if (!Character.isWhitespace(cs[i])) {
                return false
            }
        }
        return true
    }
}