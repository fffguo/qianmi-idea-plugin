package com.github.qianmi.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * es构造器
 */
class EsBuilderAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        throw RuntimeException("开发中的功能")
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isVisible = false

    }


}