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

    /**
     * public int indexOf(int ch, int fromIndex)
     * 返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索
     *
     * @return
     */
    @JvmStatic
    fun appearNumber(srcText: String, findText: String): Int {
        var count = 0;
        var index = 0;
        while (index != -1) {
            index = srcText.indexOf(findText, index + findText.length)
            count++;
        }
        return count;
    }
}