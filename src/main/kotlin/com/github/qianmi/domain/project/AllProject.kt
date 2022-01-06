package com.github.qianmi.domain.project

import com.github.qianmi.domain.project.link.*
import com.github.qianmi.domain.project.link.console.ConsoleOfD2pMc
import com.github.qianmi.domain.project.link.console.ConsoleOfOms
import com.github.qianmi.domain.project.link.console.ConsoleOfPc
import com.github.qianmi.domain.project.tools.DubboAdminInvoke
import com.github.qianmi.domain.project.tools.JPackage
import com.github.qianmi.domain.project.tools.Shell
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

object AllProject {

    private var projectMap: HashMap<String, MyProject> = HashMap()

    fun currentProject(e: AnActionEvent): MyProject {
        return currentProject(e.project)
    }

    fun currentProject(project: Project?): MyProject {
        if (project == null) {
            return currentProject("")
        }
        return currentProject(project.name)
    }

    @Synchronized
    private fun currentProject(projectName: String): MyProject {
        var myProject = projectMap[projectName]
        if (myProject == null) {
            myProject = MyProject(projectName)
            projectMap[projectName] = myProject
            return myProject
        }
        return myProject
    }


    class MyProject {
        /**
         * 项目名称
         */
        var name: String = ""

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
        var jenkins: Jenkins = Jenkins.defaultJenkins()
        var gitBook: GitBook = GitBook.defaultGitBook()
        var consoleOfD2pMc: ConsoleOfD2pMc = ConsoleOfD2pMc.defaultConsoleOfD2pMc()
        var consoleOfOms: ConsoleOfOms = ConsoleOfOms.defaultConsoleOfOms()
        var consoleOfPc: ConsoleOfPc = ConsoleOfPc.defaultConsoleOfPc()
        var jPackage: JPackage = JPackage.defaultJPackage()

        constructor(name: String) {
            this.name = name
        }
    }
}