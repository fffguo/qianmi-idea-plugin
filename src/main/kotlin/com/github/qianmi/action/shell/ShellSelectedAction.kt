package com.github.qianmi.action.shell

import com.github.qianmi.infrastructure.domain.project.tools.ShellElement
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


abstract class ShellSelectedAction : AnAction() {
    abstract var ele: ShellElement

    /**
     * 开启
     */
    abstract fun open(e: AnActionEvent)

    override fun actionPerformed(e: AnActionEvent) {
        this.open(e)
    }
}