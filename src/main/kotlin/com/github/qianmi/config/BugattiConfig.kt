package com.github.qianmi.config

import com.github.qianmi.MyBundle

object BugattiConfig {

    @JvmStatic
    val domain = MyBundle.getValue("bugatti.url")

    @JvmStatic
    val userName = MyBundle.getValue("bugatti.username")

    @JvmStatic
    val password = MyBundle.getValue("bugatti.password")

}


