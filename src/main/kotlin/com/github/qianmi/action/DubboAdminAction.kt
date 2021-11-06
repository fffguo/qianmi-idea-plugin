package com.github.qianmi.action

import com.github.qianmi.project.MyProject
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class DubboAdminAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(MyProject.dubboAdmin.getDubboAdminUrl())
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = MyProject.dubboAdmin.isSupport
    }


}