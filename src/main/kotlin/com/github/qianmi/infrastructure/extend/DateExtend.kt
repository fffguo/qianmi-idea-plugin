package com.github.qianmi.infrastructure.extend

import cn.hutool.core.date.DateUtil
import java.util.*

object DateExtend {
    /**
     * @return yyyy-MM-dd HH:mm:ss
     */
    fun Date.formatString(): String {
        return DateUtil.formatDateTime(this)
    }
}