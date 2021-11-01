package com.github.qianmi.action

import com.github.qianmi.util.ProjectUtil
import com.github.qianmi.util.QianmiIconUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class EsAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
//        BrowserUtil.open(project.getBugattiUrl(Env.TEST2))
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = ProjectUtil.getProject(e) != null
        e.presentation.icon = QianmiIconUtil.es

    }


}