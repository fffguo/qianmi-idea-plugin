package com.github.qianmi.action.dubbo

import com.github.qianmi.infrastructure.domain.project.IdeaProject
import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod

class DubboInvokeAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        e.getData(CommonDataKeys.PSI_FILE)
        val psiElement: PsiElement = CommonDataKeys.PSI_ELEMENT.getData(e.dataContext)!!
        if (psiElement is PsiMethod) {
            DubboInvokeDialog(e.project, psiElement).show()
        }
    }

    override fun update(e: AnActionEvent) {
        val psiElement: PsiElement? = CommonDataKeys.PSI_ELEMENT.getData(e.dataContext)
        e.presentation.isEnabled =
            IdeaProject.getInstance(e).dubboInvoke.isSupport
                    && psiElement != null
                    && psiElement.language is JavaLanguage
                    && psiElement is PsiMethod
    }


}