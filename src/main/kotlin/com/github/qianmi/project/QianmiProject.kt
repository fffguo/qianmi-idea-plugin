package com.github.qianmi.project

abstract class QianmiProject {

    /**
     * 项目名称
     */
    abstract val name: String

    /**
     * 项目描述
     */
    abstract val desc: String

    /**
     * 布加迪
     */
    abstract val bugatti: Bugatti

    /**
     * gitlab
     */
    abstract val gitlab: Gitlab

    /**
     * dubboAdmin
     */
    abstract val dubboAdmin: DubboAdmin

    /**
     * shell
     */
    abstract val shell: Shell
}