package com.github.qianmi.action.shell.jvm

import com.github.qianmi.action.shell.ShellAction
import com.github.qianmi.domain.project.tools.ShellElement

class RemoteJVMAction : ShellAction<RemoteSelectedAction>() {

    override fun instanceShellSelectedAction(ele: ShellElement): RemoteSelectedAction {
        return RemoteSelectedAction(ele)
    }


}
