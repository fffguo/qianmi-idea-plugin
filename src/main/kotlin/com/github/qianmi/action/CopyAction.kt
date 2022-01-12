package com.github.qianmi.action

import com.github.qianmi.infrastructure.util.CopyUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class CopyAction : AnAction() {
    //剪贴板文案
    var pasteText: String = ""


    override fun actionPerformed(e: AnActionEvent) {
        CopyUtil.copy(this.pasteText)
    }


    companion object {
        fun instanceOf(presentText: String, pasteText: String): CopyAction {
            val copyAction = CopyAction()
            copyAction.pasteText = pasteText
            copyAction.templatePresentation.text = presentText
            return copyAction
        }
    }
}