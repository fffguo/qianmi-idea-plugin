package com.github.qianmi.util

import cn.hutool.core.util.StrUtil

object StringUtil {
    /**
     * @return 检索指定字符出现的次数
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


    fun String.isNotBlank(): Boolean {
        return StrUtil.isNotBlank(this)
    }
}