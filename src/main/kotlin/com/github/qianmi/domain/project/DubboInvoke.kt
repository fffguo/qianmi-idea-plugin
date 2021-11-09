package com.github.qianmi.domain.project

import com.alibaba.fastjson.JSONObject
import com.github.qianmi.action.dubboinvoke.ArgumentDialog
import com.github.qianmi.util.MyPSIUtils
import com.intellij.psi.PsiMethod
import org.apache.commons.io.FilenameUtils
import java.util.stream.Collectors

private const val formatUrl = "invoke {packageName}.{className}.{methodName} ({argJson})"

class DubboAdminInvoke(
    /**
     * 是否支持
     */
    var isSupport: Boolean,
) {


    companion object {
        fun defaultDubboAdminInvoke(): DubboAdminInvoke {
            return DubboAdminInvoke(false)
        }

        fun getInvokeCommand(methodPsi: PsiMethod, argList: Map<String, ArgumentDialog.Arg>): String {
            val psiJavaFile = MyPSIUtils.getContainingPsiJavaFile(methodPsi)
            return formatUrl
                .replace("{packageName}", psiJavaFile.packageName)
                .replace("{className}", FilenameUtils.getBaseName(psiJavaFile.containingFile.name))
                .replace("{methodName}", MyPSIUtils.getMethodName(methodPsi))
                .replace("{argJson}", argList.values.stream()
                    .map { arg -> JSONObject.toJSONString(JSONObject.parse(arg.jTextArea.text)) }
                    .collect(Collectors.joining(",")))
        }
    }


}