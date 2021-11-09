package com.github.qianmi.util

import cn.hutool.core.date.DateUtil
import com.alibaba.fastjson.JSONObject
import com.github.qianmi.domain.vo.KV
import com.intellij.psi.PsiArrayType
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiEnumConstant
import com.intellij.psi.PsiPrimitiveType
import com.intellij.psi.util.PsiTypesUtil
import com.intellij.psi.util.PsiUtil
import com.intellij.util.containers.isEmpty
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


object JsonConverter {
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
        "LocalDateTime" to LocalDateTime.now().toString())

    @JvmStatic
    private fun isNormalType(typeName: String): Boolean {
        return normalTypes.containsKey(typeName)
    }

    @JvmStatic
    fun isIgnoreType(name: String): Boolean {
        return !normalTypes.keys.stream().filter { key -> name.lowercase().contains(key.lowercase()) }.isEmpty()
    }

    @JvmStatic
    fun classToJsonString(psiClass: PsiClass?): String {
        val kv: KV<String, Any> = KV.create()
        if (psiClass != null) {

            for (field in psiClass.allFields) {
                val type = field.type
                val name = field.name

                if (name == "serialVersionUID") {
                    continue
                }
                //primitive Type
                if (type is PsiPrimitiveType) {
                    kv[name] = PsiTypesUtil.getDefaultValue(type)
                } else {
                    //reference Type
                    val fieldTypeName = type.presentableText
                    //normal Type
                    if (isNormalType(fieldTypeName)) {
                        if ("String" == fieldTypeName) {
                            kv[name] = name
                        } else {
                            normalTypes[fieldTypeName]?.let { kv.set(name, it) }
                        }
                    }
                    //array type
                    else if (type is PsiArrayType) {
                        val deepType = type.getDeepComponentType()
                        val list = ArrayList<Any>()
                        val deepTypeName = deepType.presentableText
                        if (deepType is PsiPrimitiveType) {
                            list.add(PsiTypesUtil.getDefaultValue(deepType))
                        } else if (isNormalType(deepTypeName)) {
                            normalTypes[deepTypeName]?.let { list.add(it) }
                        } else {
                            list.add(classToJsonString(PsiUtil.resolveClassInType(deepType)))
                        }
                        kv[name] = list
                    }
                    //list type
                    else if (fieldTypeName.startsWith("List")) {
                        val iterableType = PsiUtil.extractIterableTypeParameter(type, false)
                        val iterableClass = PsiUtil.resolveClassInClassTypeOnly(iterableType)
                        val list = ArrayList<Any>()
                        val classTypeName = iterableClass!!.name!!
                        if (isNormalType(classTypeName)) {
                            normalTypes[classTypeName]?.let { list.add(it) }
                        } else {
                            list.add(classToJsonString(iterableClass))
                        }
                        kv[name] = list
                    }
                    //enum
                    else if (PsiUtil.resolveClassInClassTypeOnly(type)!!.isEnum) {
                        val nameList = ArrayList<String>()
                        val fieldList = PsiUtil.resolveClassInClassTypeOnly(type)!!.fields
                        for (f in fieldList) {
                            if (f is PsiEnumConstant) {
                                nameList.add(f.getName())
                            }
                        }
                        kv[name] = nameList
                    } else {
                        kv[name] = classToJsonString(PsiUtil.resolveClassInType(type))
                    }
                }
            }
        }
        return JSONObject.toJSONString(kv)
    }
}