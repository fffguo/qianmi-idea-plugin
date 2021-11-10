package com.github.qianmi.action

import com.github.qianmi.domain.project.MyProject
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class RocketMqAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open(MyProject.rocketMq.getRocketMqUrl())
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = MyProject.rocketMq.isSupport
    }


}