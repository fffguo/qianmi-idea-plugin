package com.github.qianmi.util

import com.intellij.util.containers.isNullOrEmpty

object CollectionUtil {

    inline fun <reified T> List<T>?.isNotNullOrEmpty(): Boolean {
        return !this.isNullOrEmpty()
    }

    /**
     * 取list第一个值，如果list值为空，则取默认值
     */
    inline fun <reified T> List<T>?.firstDefault(default: T): T {
        if (this.isNotNullOrEmpty()) {
            return this!![0]
        }
        return default
    }
}