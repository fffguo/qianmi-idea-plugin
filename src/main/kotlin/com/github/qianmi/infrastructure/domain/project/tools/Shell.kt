package com.github.qianmi.infrastructure.domain.project.tools

import com.github.qianmi.infrastructure.domain.enums.EnvEnum
import com.github.qianmi.infrastructure.storage.EnvConfig
import java.nio.charset.Charset

class Shell(

    /**
     * 是否支持shell
     */
    var isSupport: Boolean,

    /**
     * 节点信息
     */
    var eleMap: HashMap<EnvEnum, List<ShellElement>>,

    ) {
    companion object {
        fun defaultShell(): Shell {
            return Shell(false, hashMapOf(EnvConfig.getInstance().env to listOf()))
        }
    }


    fun getShellCharset(): Charset {
        return Charset.forName("utf-8")
    }

}