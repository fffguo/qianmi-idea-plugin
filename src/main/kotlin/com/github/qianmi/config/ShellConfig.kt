package com.github.qianmi.config

import com.github.qianmi.MyBundle

object ShellConfig {

    @JvmStatic
    val username = MyBundle.getValue("shell.username")

    @JvmStatic
    val password = MyBundle.getValue("shell.password")

    @JvmStatic
    val port = MyBundle.getValue("shell.port")

}


