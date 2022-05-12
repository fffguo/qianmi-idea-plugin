package com.github.qianmi.action.shell.ssh.command.log

import com.github.qianmi.action.shell.ShellAction
import com.github.qianmi.action.shell.ssh.command.SshCommandSelectedAction
import com.github.qianmi.infrastructure.domain.project.tools.ShellElement
import com.github.qianmi.infrastructure.storage.ProjectConfig

/**
 * 一键查看服务器日志
 */
class TailLogAction : ShellAction<SshCommandSelectedAction>() {

    override fun instanceShellSelectedAction(ele: ShellElement): SshCommandSelectedAction {
        return SshCommandSelectedAction(ele) { e -> e.getService(ProjectConfig::class.java).logCommand }
    }
}