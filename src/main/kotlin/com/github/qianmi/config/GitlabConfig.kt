package com.github.qianmi.config

import com.github.qianmi.MyBundle

object GitlabConfig {

    @JvmStatic
    val domain = MyBundle.getValue("gitlab.url")

}


