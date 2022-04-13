package com.github.qianmi.infrastructure.domain.project.tools

import com.github.qianmi.action.dubbo.DubboInvokeDialog
import com.github.qianmi.infrastructure.util.JsonUtil
import com.github.qianmi.infrastructure.util.MyPsiUtil
import com.intellij.psi.PsiMethod
import org.apache.commons.io.FilenameUtils
import java.util.stream.Collectors


class DubboAdminInvoke(
    /**
     * 是否支持
     */
    var isSupport: Boolean,
) {

    companion object {

        private const val formatUrl = "invoke {packageName}.{className}.{methodName} ({argJson})"

        fun getInvokeCommand(methodPsi: PsiMethod, argList: Map<String, DubboInvokeDialog.Arg>): String {
            val psiJavaFile = MyPsiUtil.getContainingPsiJavaFile(methodPsi)
            return formatUrl
                .replace("{packageName}", psiJavaFile.packageName)
                .replace("{className}", FilenameUtils.getBaseName(psiJavaFile.containingFile.name))
                .replace("{methodName}", MyPsiUtil.getMethodName(methodPsi))
                .replace("{argJson}", argList.values
                    .stream()
                    .map { arg ->
                        JsonUtil.zipJson(arg.jTextArea.text)
                    }
                    .collect(Collectors.joining(",")))
        }
    }


}