package com.github.qianmi.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    private val sdf = SimpleDateFormat(NORM_DATETIME_PATTERN)

    fun Date.formatString(): String {
        return sdf.format(this)
    }

}