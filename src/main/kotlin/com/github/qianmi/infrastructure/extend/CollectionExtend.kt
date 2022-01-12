package com.github.qianmi.infrastructure.extend

import cn.hutool.core.collection.CollectionUtil.getFirst
import cn.hutool.core.collection.CollectionUtil.isNotEmpty

object CollectionExtend {

    inline fun <reified T> List<T>?.isNotEmpty(): Boolean {
        return isNotEmpty(this)
    }

    /**
     * 取list第一个值，如果list值为空，则取默认值
     */
    inline fun <reified T> List<T>?.firstDefault(default: T): T {
        return getFirst(this) ?: return default
    }
}