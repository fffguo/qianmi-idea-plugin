package com.github.qianmi.action.shell

import com.github.qianmi.domain.project.Shell
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


class ShellSelectedAction(var ele: Shell.Element) : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        ele.openSshTerminal(e.project!!)
    }
}