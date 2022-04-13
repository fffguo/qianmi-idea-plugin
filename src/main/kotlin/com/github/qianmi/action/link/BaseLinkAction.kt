package com.github.qianmi.action.link

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.github.qianmi.infrastructure.domain.project.link.BaseLink
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

abstract class BaseLinkAction : AnAction() {

    /**
     * 获取BaseLink项目
     */
    abstract fun getLinkProject(myProject: IdeaProject.MyProject): BaseLink


    override fun actionPerformed(e: AnActionEvent) {
        getLinkProject(IdeaProject.getInstance(e)).openBrowser(e.project!!)
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = getLinkProject(IdeaProject.getInstance(e)).isSupport
    }


}