package com.github.qianmi.action

import com.github.qianmi.util.QianmiIconUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup

/**
 * 千米 group
 */
class QianmiActionGroup : DefaultActionGroup() {
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = true
        e.presentation.icon = QianmiIconUtil.qianmi
    }

}