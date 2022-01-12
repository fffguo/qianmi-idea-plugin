package com.github.qianmi.infrastructure.util

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


}