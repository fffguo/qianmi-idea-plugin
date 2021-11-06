package com.github.qianmi.project

import com.github.qianmi.enums.EnvEnum

object MyProject {

    /**
     * 项目名称
     */
    var name: String = ""

    /**
     * 项目环境
     */
    var env: EnvEnum = EnvEnum.TEST1

    /**
     * dubboAdmin
     */
    var dubboAdmin: DubboAdmin = DubboAdmin.defaultDubboAdmin()

    /**
     * 布加迪
     */
    var bugatti: Bugatti = Bugatti.defaultBugatti()

    /**
     * gitlab
     */
    val gitlab: Gitlab = Gitlab.defaultGitlab()

    /**
     * shell
     */
    var shell: Shell = Shell.defaultShell()
}