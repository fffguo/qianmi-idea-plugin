package com.github.qianmi.action.shell.ssh

import com.github.qianmi.domain.project.tools.Shell
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


class SshSelectedAction(var ele: Shell.Element) : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        ele.openSshTerminal(e.project!!)
    }
}