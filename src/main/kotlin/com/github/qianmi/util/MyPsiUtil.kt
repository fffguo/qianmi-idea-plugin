package com.github.qianmi.util

import cn.hutool.core.date.DateUtil
import com.intellij.psi.*
import com.intellij.psi.util.PsiUtil
import com.intellij.util.containers.isEmpty
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MyPsiUtil {

    companion object {
        var normalTypes = hashMapOf<String, Any>(
            "Boolean" to false,
            "Byte" to 0,
            "Short" to 0,
            "Integer" to 0,
            "int" to 0,
            "Long" to 0L,
            "Float" to 0.0F,
            "Double" to 0.0,
            "String" to "",
            "BigDecimal" to 0.0,
            "Date" to DateUtil.now(),
            "Timestamp" to System.currentTimeMillis(),
            "LocalDate" to LocalDate.now().toString(),
            "LocalTime" to LocalTime.now().toString(),
            "LocalDateTime" to LocalDateTime.now().toString(),
            "Object" to "")

        fun isNormalType(typeName: String): Boolean {
            return normalTypes.containsKey(typeName)
        }

        fun isIgnoreType(name: String): Boolean {
            if (name.contains("[]")) {
                return false
            }
            return !normalTypes.keys.stream().filter { key -> name.lowercase().contains(key.lowercase()) }.isEmpty()
        }

        /**
         * 是否为 list
         */
        fun isList(psiType: PsiType): Boolean {
            return isList(psiType.presentableText)
        }

        fun isList(psiClass: PsiClass): Boolean {
            return isList(psiClass.name!!)
        }

        private fun isList(name: String): Boolean {
            return name.startsWith("List")
                    || name.startsWith("ArrayList")
                    || name.startsWith("LinkedList")
                    || name.startsWith("Vector")
                    || name.startsWith("Stack")
                    || name.startsWith("Set")
                    || name.startsWith("TreeSet")
                    || name.startsWith("HashSet")
                    || name.startsWith("LinkedHashSet")
        }

        /**
         * 是否为 array
         */
        fun isArray(psiType: PsiType): Boolean {
            return psiType is PsiArrayType
        }

        /**
         * 是否为 map
         */
        fun isMap(psiType: PsiType): Boolean {
            val fieldTypeName = psiType.presentableText
            return isMap(fieldTypeName)
        }

        /**
         * 是否为 map
         */
        private fun isMap(str: String): Boolean {
            return str.startsWith("Node")
                    || str.startsWith("HashMap")
                    || str.startsWith("LinkedHashMap")
                    || str.startsWith("TreeMap")
                    || str.startsWith("Entry")
                    || str.startsWith("Map")
        }

        /**
         * 是否为 enum
         */
        fun isEnum(psiType: PsiType): Boolean {
            return PsiUtil.resolveClassInClassTypeOnly(psiType)!!.isEnum
        }

        /**
         * 静态方法
         *
         * @param psiElement
         * @return
         */
        private fun isStaticMethod(psiElement: PsiElement?): Boolean {
            var result = false
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                if (psiElement is PsiMethod) {
                    if (psiElement.hasModifierProperty(PsiModifier.STATIC)) {
                        result = true
                    }
                }
            }
            return result
        }

        /**
         * 静态字段
         *
         * @param psiElement
         * @return
         */
        private fun isStaticField(psiElement: PsiElement?): Boolean {
            var result = false
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                if (psiElement is PsiField) {
                    if (psiElement.hasModifierProperty(PsiModifier.STATIC)) {
                        result = true
                    }
                }
            }
            return result
        }

        /**
         * 这个是非静态的方法或者字段
         *
         * @param psiElement
         * @return
         */
        fun isNonStaticMethodOrField(psiElement: PsiElement?): Boolean {
            var result = false
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                if (psiElement is PsiMethod) {
                    if (!isStaticMethod(psiElement)) {
                        result = true
                    }
                } else if (psiElement is PsiField) {
                    if (!isStaticField(psiElement)) {
                        result = true
                    }
                }
            }
            return result
        }

        /**
         * 获取方法名称
         *
         * @param psiElement
         * @return
         */
        fun getMethodName(psiElement: PsiElement?): String {
            var methodName = ""
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                methodName = "*"
                if (psiElement is PsiMethod) {
                    methodName = if (psiElement.nameIdentifier != null) {
                        psiElement.nameIdentifier!!.text
                    } else {
                        psiElement.name
                    }
                    if (psiElement.isConstructor) {
                        methodName = "<init>"
                    }
                }
            }
            return methodName
        }

        /**
         * 获取可以执行的参数
         *
         * @param psiElement
         * @return
         */
        fun getExecuteInfo(psiElement: PsiElement?): String {
            if (psiElement is PsiField) {
                return psiElement.nameIdentifier.text
            } else if (psiElement is PsiMethod) {
                return getMethodParameterDefault(psiElement)
            }
            return ""
        }

        /**
         * 构造方法的参数信息  complexParameterCall(#{" ":" "}) 后面这个部分需要构造
         *
         * @param psiMethod
         * @return
         */
        private fun getMethodParameterDefault(psiMethod: PsiMethod): String {
            // Experimental API method JvmField.getName() is invoked in Action.arthas.ArthasOgnlStaticCommandAction.actionPerformed().
            // This method can be changed in a future release leading to incompatibilities
            val methodName = getMethodName(psiMethod)
            val builder = StringBuilder(methodName).append("(")
            val parameters = psiMethod.parameterList.parameters
            if (parameters.isNotEmpty()) {
                for ((index, parameter) in parameters.withIndex()) {
                    val defaultParamValue = getDefaultString(parameter.type)
                    builder.append(defaultParamValue)
                    if (index != parameters.size - 1) {
                        builder.append(",")
                    }
                }
            }
            builder.append(")")
            return builder.toString()
        }

        /**
         * 构造ognl 的默认值
         *
         * @param psiType
         * @return
         */
        private fun getDefaultString(psiType: PsiType): String {
            val result: String
            val canonicalText = psiType.canonicalText

            //基本类型  boolean
            if (PsiType.BOOLEAN == psiType || "java.lang.Boolean" == canonicalText) {
                result = "true"
                return result
            }

            //基本类型  String
            if (canonicalText.endsWith("java.lang.String")) {
                result = "\" \""
                return result
            }
            if (PsiType.LONG == psiType || "java.lang.Long" == canonicalText) {
                result = "0L"
                return result
            }
            if (PsiType.DOUBLE == psiType || "java.lang.Double" == canonicalText) {
                result = "0D"
                return result
            }
            if (PsiType.FLOAT == psiType || "java.lang.Float" == canonicalText) {
                result = "0F"
                return result
            }

            //基本类型  数字
            if (PsiType.INT == psiType || "java.lang.Integer" == canonicalText || PsiType.BYTE == psiType || "java.lang.Byte" == canonicalText || PsiType.SHORT == psiType || "java.lang.Short" == canonicalText) {
                result = "0"
                return result
            }


            //Class xx 特殊class 字段的判断
            //java.lang.Class
            if ("java.lang.Class" == canonicalText) {
                result = "@java.lang.Object@class"
                return result
            }
            //Class<XXX> x
            //java.lang.Class<com.wangji92.arthas.plugin.demo.controller.user>
            if (canonicalText.startsWith("java.lang.Class")) {
                result =
                    "@" + canonicalText.substring(canonicalText.indexOf("<") + 1, canonicalText.length - 1) + "@class"
                return result
            }


            //常见的List 和Map
            if (canonicalText.startsWith("java.util.")) {
                if (canonicalText.contains("Map")) {
                    result = "#{\" \": null }"
                    return result
                }
                if (canonicalText.contains("List")) {
                    result = "{}"
                    return result
                }
            }

            //...
            if (psiType is PsiEllipsisType) {
                val arrayCanonicalText = psiType.deepComponentType.canonicalText
                return "new $arrayCanonicalText[]{}"
            }

            //原生的数组
            if (canonicalText.contains("[]")) {
                result = "new $canonicalText{}"
                return result
            }

            //不管他的构造函数了，太麻烦了
            result = "new $canonicalText()"
            return result
        }

        /**
         * 当前是psi 的这个几种类型？ psiElement instanceof JvmMember 兼容性不好 修改为这个 Experimental API interface JvmElement is. This interface can be changed in a future release leading to incompatibilities
         *
         * @param psiElement
         * @return
         */
        private fun isPsiFieldOrMethodOrClass(psiElement: PsiElement?): Boolean {
            return psiElement is PsiField || psiElement is PsiClass || psiElement is PsiMethod || psiElement is PsiJavaFile
        }

        /**
         * 获取ContainingPsiJavaFile
         *
         * @param psiElement
         * @return
         */
        fun getContainingPsiJavaFile(psiElement: PsiElement): PsiJavaFile {
            if (psiElement is PsiField || psiElement is PsiClass || psiElement is PsiMethod) {
                return psiElement.containingFile as PsiJavaFile
            } else if (psiElement is PsiJavaFile) {
                return psiElement
            }
            throw IllegalArgumentException("Illegal parameters")
        }
    }

}