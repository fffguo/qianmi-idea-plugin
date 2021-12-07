package com.github.qianmi.domain.project.tools

import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.AllProject
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
            return Shell(isSupport = false, eleMap = hashMapOf(AllProject.defaultEnv to listOf()))
        }
    }


    fun getShellCharset(): Charset {
        return Charset.forName("utf-8")
    }

}