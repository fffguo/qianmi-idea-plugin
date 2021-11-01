package com.github.qianmi.action

import com.github.qianmi.enums.BugattiEnum
import com.github.qianmi.util.ProjectUtil
import com.github.qianmi.util.QianmiIconUtil
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class BugattiAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(ProjectUtil.getProject(e)!!.getBugattiUrl(BugattiEnum.TEST2))
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = ProjectUtil.getProject(e) != null
        e.presentation.icon = QianmiIconUtil.bugatti
    }


}