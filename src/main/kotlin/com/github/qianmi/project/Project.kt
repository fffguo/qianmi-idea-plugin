package com.github.qianmi.project

abstract class Project : Bugatti, Gitlab, DubboAdmin,Shell {
    /**
     * 项目名称
     */
    abstract val name: String

    /**
     * 项目描述
     */
    abstract val desc: String
}