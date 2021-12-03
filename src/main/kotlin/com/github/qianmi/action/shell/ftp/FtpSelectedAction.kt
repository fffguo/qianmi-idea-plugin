package com.github.qianmi.action.shell.ftp

import com.github.qianmi.domain.project.tools.Shell
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


class FtpSelectedAction(var ele: Shell.Element) : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        ele.openSftp(e.project!!)
    }
}