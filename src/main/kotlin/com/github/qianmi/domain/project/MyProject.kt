package com.github.qianmi.domain.project

import com.github.qianmi.domain.enums.EnvEnum
import com.github.qianmi.domain.project.link.*
import com.github.qianmi.domain.project.link.console.ConsoleOfD2pMc
import com.github.qianmi.domain.project.link.console.ConsoleOfOms
import com.github.qianmi.domain.project.link.console.ConsoleOfPc
import com.github.qianmi.domain.project.tools.DubboAdminInvoke
import com.github.qianmi.domain.project.tools.Shell

object MyProject {

    /**
     * 项目名称
     */
    var name: String = ""

    /**
     * 项目环境
     */
    var env: EnvEnum = EnvEnum.TEST1

    var dubboAdmin: DubboAdmin = DubboAdmin.defaultDubboAdmin()
    var bugatti: Bugatti = Bugatti.defaultBugatti()
    val gitlab: Gitlab = Gitlab.defaultGitlab()
    var shell: Shell = Shell.defaultShell()
    var dubboInvoke: DubboAdminInvoke = DubboAdminInvoke.defaultDubboAdminInvoke()
    var rocketMq: RocketMq = RocketMq.defaultRocketMq()
    var activeMq: ActiveMq = ActiveMq.defaultActiveMq()
    var gavin: Gavin = Gavin.defaultGavin()
    var trace: Trace = Trace.defaultTrace()
    var qianmiAdmin: QianmiAdmin = QianmiAdmin.defaultQianmiAdmin()
    var apmDashboard: ApmDashboard = ApmDashboard.defaultApmDashboard()
    var mongoDBManager: MongoDBManager = MongoDBManager.defaultMongoDBManager()
    var wiki: Wiki = Wiki.defaultWiki()
    var gitBook: GitBook = GitBook.defaultGitBook()
    var consoleOfD2pMc: ConsoleOfD2pMc = ConsoleOfD2pMc.defaultConsoleOfD2pMc()
    var consoleOfOms: ConsoleOfOms = ConsoleOfOms.defaultConsoleOfOms()
    var consoleOfPc: ConsoleOfPc = ConsoleOfPc.defaultConsoleOfPc()
}