package com.github.qianmi.action.shell.ssh

import com.github.qianmi.action.shell.ShellAction
import com.github.qianmi.infrastructure.domain.project.tools.ShellElement


class SshAction : ShellAction<SshSelectedAction>() {

    override fun instanceShellSelectedAction(ele: ShellElement): SshSelectedAction {
        return SshSelectedAction(ele)
    }
}