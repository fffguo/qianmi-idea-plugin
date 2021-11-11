package com.github.qianmi.util

import cn.hutool.core.date.DateUtil
import com.google.common.collect.Sets
import com.intellij.psi.*
import com.intellij.psi.util.PsiUtil
import com.intellij.util.containers.isEmpty
import org.apache.commons.io.FilenameUtils
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

        fun isList(name: String): Boolean {
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
        fun isArray(psiClass: PsiClass): Boolean {
            return psiClass is PsiArrayType
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
        fun isMap(str: String): Boolean {
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
         * 是否为匿名类
         *
         * @param psiElement
         * @return
         */
        fun isAnonymousClass(psiElement: PsiElement): Boolean {
            var result = false
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                if (getCommonOrInnerOrAnonymousClassName(psiElement).contains("*$*")) {
                    result = true
                }
            }
            return result
        }

        /**
         * 是否为静态的字段或者方法
         *
         * @param psiElement
         * @return
         */
        fun isStaticMethodOrField(psiElement: PsiElement?): Boolean {
            val result = false
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                if (psiElement is PsiMethod) {
                    return isStaticMethod(psiElement)
                } else if (psiElement is PsiField) {
                    return isStaticField(psiElement)
                }
            }
            return result
        }

        /**
         * 静态方法
         *
         * @param psiElement
         * @return
         */
        fun isStaticMethod(psiElement: PsiElement?): Boolean {
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
        fun isStaticField(psiElement: PsiElement?): Boolean {
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
         * isFinalField
         *
         * @param psiElement
         * @return
         */
        fun isFinalField(psiElement: PsiElement?): Boolean {
            var result = false
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                if (psiElement is PsiField) {
                    if (psiElement.hasModifierProperty(PsiModifier.FINAL)) {
                        result = true
                    }
                }
            }
            return result
        }

        /**
         * 非静态字段
         *
         * @param psiElement
         * @return
         */
        fun isNonStaticField(psiElement: PsiElement?): Boolean {
            var result = false
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                if (psiElement is PsiField) {
                    if (!isStaticField(psiElement)) {
                        result = true
                    }
                }
            }
            return result
        }

        /**
         * 非静态方法
         *
         * @param psiElement
         * @return
         */
        fun isNonStaticMethod(psiElement: PsiElement?): Boolean {
            var result = false
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                if (psiElement is PsiMethod) {
                    if (!isStaticMethod(psiElement)) {
                        result = true
                    }
                }
            }
            return result
        }

        /**
         * 是否为构造方法
         *
         * @param psiElement
         * @return
         */
        fun isConstructor(psiElement: PsiElement?): Boolean {
            var result = false
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                if (psiElement is PsiMethod) {
                    if (psiElement.isConstructor) {
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
         * 获取内部类、匿名类的class的 ognl 名称
         *
         * @param psiElement
         * @return
         */
        fun getCommonOrInnerOrAnonymousClassName(psiElement: PsiElement): String {
            if (!isPsiFieldOrMethodOrClass(psiElement)) {
                return ""
            }
            if (psiElement is PsiMethod) {
                return getCommonOrInnerOrAnonymousClassName(psiElement)
            }
            if (psiElement is PsiField) {
                return getCommonOrInnerOrAnonymousClassName(psiElement)
            }
            if (psiElement is PsiClass) {
                return getCommonOrInnerOrAnonymousClassName(psiElement)
            }
            if (psiElement is PsiJavaFile) {
                //only select project file is PsiJavaFile
                val packageName = (psiElement.getContainingFile() as PsiJavaFile).packageName
                val shortClassName = FilenameUtils.getBaseName(psiElement.getContainingFile().name)
                return "$packageName.$shortClassName"
            }
            throw IllegalArgumentException("Illegal parameters")
        }


        /**
         * 字段的名称
         *
         * @param psiElement
         * @return
         */
        fun getFieldName(psiElement: PsiElement?): String {
            var fieldName = ""
            if (isPsiFieldOrMethodOrClass(psiElement)) {
                if (psiElement is PsiField) {
                    fieldName = psiElement.nameIdentifier.text
                }
            }
            return fieldName
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
                    val psiMethod = psiElement
                    methodName = if (psiMethod.nameIdentifier != null) {
                        psiMethod.nameIdentifier!!.text
                    } else {
                        psiMethod.name
                    }
                    if (psiMethod.isConstructor) {
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
        fun getMethodParameterDefault(psiMethod: PsiMethod): String {
            // Experimental API method JvmField.getName() is invoked in Action.arthas.ArthasOgnlStaticCommandAction.actionPerformed().
            // This method can be changed in a future release leading to incompatibilities
            val methodName = getMethodName(psiMethod)
            val builder = StringBuilder(methodName).append("(")
            val parameters = psiMethod.parameterList.parameters
            if (parameters.size > 0) {
                var index = 0
                for (parameter in parameters) {
                    val defaultParamValue = getDefaultString(parameter.type)
                    builder.append(defaultParamValue)
                    if (index != parameters.size - 1) {
                        builder.append(",")
                    }
                    index++
                }
            }
            builder.append(")")
            return builder.toString()
        }

        /**
         * 获取字段的默认值
         *
         * @return
         */
        fun getFieldDefaultValue(psiElement: PsiElement?): String {
            var defaultFieldValue = ""
            if (psiElement is PsiField) {
                defaultFieldValue = getDefaultString(psiElement.type)
            }
            return defaultFieldValue
        }

        /**
         * 构造ognl 的默认值
         *
         * @param psiType
         * @return
         */
        fun getDefaultString(psiType: PsiType): String {
            var result = " "
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
         * 当前是否为spring bean
         *
         * @return
         */
        fun isSpringBean(psiElement: PsiElement?): Boolean {
            val result = false
            if (!isPsiFieldOrMethodOrClass(psiElement)) {
                return result
            }
            var psiClass: PsiClass? = null
            if (psiElement is PsiField) {
                val field = psiElement
                val annotations = field.annotations
                val annotationTypes: MutableSet<String?> = HashSet()
                annotationTypes.add("org.springframework.beans.factory.annotation.Autowired")
                annotationTypes.add("org.springframework.beans.factory.annotation.Qualifier")
                annotationTypes.add("javax.annotation.Resource")
                annotationTypes.add("org.springframework.beans.factory.annotation.Value")
                for (annotation in annotations) {
                    if (annotationTypes.contains(annotation.qualifiedName)) {
                        return true
                    }
                }
                psiClass = field.containingClass
            }
            if (psiElement is PsiMethod) {
                val psiMethod = psiElement
                val annotations = psiMethod.annotations
                val annotationTypes: MutableSet<String?> = HashSet()
                annotationTypes.add("javax.annotation.PostConstruct")
                annotationTypes.add("javax.annotation.PreDestroy")
                annotationTypes.add("javax.annotation.Resource")
                annotationTypes.add("org.springframework.beans.factory.annotation.Lookup")
                annotationTypes.add("org.springframework.context.annotation.Bean")
                annotationTypes.add("org.springframework.context.annotation.Conditional")
                annotationTypes.add("org.springframework.context.annotation.Scope")
                for (annotation in annotations) {
                    if (annotationTypes.contains(annotation.qualifiedName)) {
                        return true
                    }
                }
                psiClass = psiMethod.containingClass
            }
            psiClass = psiElement as PsiClass?
            val annotationTypes = Sets.newHashSet<String?>()
            annotationTypes.add("org.springframework.stereotype.Service")
            annotationTypes.add("org.springframework.stereotype.Controller")
            annotationTypes.add("org.springframework.stereotype.Repository")
            annotationTypes.add("org.springframework.web.bind.annotation.RestController")
            annotationTypes.add("org.springframework.context.annotation.ComponentScan")
            annotationTypes.add("org.springframework.stereotype.Component")
            annotationTypes.add("org.springframework.context.annotation.Conditional")
            annotationTypes.add("javax.annotation.Resources")
            for (annotation in psiClass!!.annotations) {
                assert(annotation != null)
                assert(annotation!!.qualifiedName != null)
                if (annotation.qualifiedName!!.startsWith("org.springframework.") || annotationTypes.contains(
                        annotation.qualifiedName)
                ) {
                    return true
                }
            }
            for (anInterface in psiClass.interfaces) {
                assert(anInterface != null)
                assert(anInterface!!.qualifiedName != null)
                if (anInterface.qualifiedName!!.startsWith("org.springframework.")) {
                    return true
                }
                //todo
            }
            if (psiClass.superClass != null) {
                assert(psiClass.superClass!!.qualifiedName != null)
                if (psiClass.superClass!!.qualifiedName!!.startsWith("org.springframework.")) {
                }
            }
            for (method in psiClass.methods) {
                assert(method != null)
                //todo
            }
            for (field in psiClass.fields) {
                assert(field != null)
                // todo
            }
            return false
        }

        /**
         * 获取注解的值得信息
         *
         * @param annotation
         * @param annotationTypes
         * @param attribute
         * @return
         */
        private fun getAttributeFromAnnotation(
            annotation: PsiAnnotation, annotationTypes: Set<String>, attribute: String,
        ): String {
            val annotationQualifiedName = annotation.qualifiedName ?: return ""
            if (annotationTypes.contains(annotationQualifiedName)) {
                val annotationMemberValue = annotation.findAttributeValue(attribute) ?: return ""
                val httpMethodWithQuotes = annotationMemberValue.text
                return httpMethodWithQuotes.substring(1, httpMethodWithQuotes.length - 1)
            }
            return ""
        }

        /**
         * 当前是psi 的这个几种类型？ psiElement instanceof JvmMember 兼容性不好 修改为这个 Experimental API interface JvmElement is. This interface can be changed in a future release leading to incompatibilities
         *
         * @param psiElement
         * @return
         */
        fun isPsiFieldOrMethodOrClass(psiElement: PsiElement?): Boolean {
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