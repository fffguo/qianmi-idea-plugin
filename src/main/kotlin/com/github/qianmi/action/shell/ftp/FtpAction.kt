package com.github.qianmi.action.shell.ftp

import com.github.qianmi.action.shell.ShellAction
import com.github.qianmi.infrastructure.domain.project.tools.ShellElement

class FtpAction : ShellAction<FtpSelectedAction>() {

    override fun instanceShellSelectedAction(ele: ShellElement): FtpSelectedAction {
        return FtpSelectedAction(ele)
    }

}