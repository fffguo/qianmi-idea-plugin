package com.github.qianmi.util

import com.github.qianmi.domain.vo.KV
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiEnumConstant
import com.intellij.psi.PsiPrimitiveType
import com.intellij.psi.PsiType
import com.intellij.psi.util.PsiTypesUtil
import com.intellij.psi.util.PsiUtil


object JsonConverter {


    @JvmStatic
    fun classToJson(psiClass: PsiClass?): Any {
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
                    if (MyPsiUtil.isNormalType(fieldTypeName)) {
                        if ("String" == fieldTypeName) {
                            kv[name] = name
                        } else {
                            MyPsiUtil.normalTypes[fieldTypeName]?.let { kv.set(name, it) }
                        }
                    }
                    //array type
                    else if (MyPsiUtil.isArray(type)) {
                        val deepType = type.deepComponentType
                        val list = ArrayList<Any>()
                        val deepTypeName = deepType.presentableText
                        if (deepType is PsiPrimitiveType) {
                            list.add(PsiTypesUtil.getDefaultValue(deepType))
                        } else if (MyPsiUtil.isNormalType(deepTypeName)) {
                            MyPsiUtil.normalTypes[deepTypeName]?.let { list.add(it) }
                        } else {
                            list.add(classToJson(PsiUtil.resolveClassInType(deepType)))
                        }
                        kv[name] = list
                    }
                    //list type
                    else if (MyPsiUtil.isList(type)) {
                        val iterableType = PsiUtil.extractIterableTypeParameter(type, false)
                        val iterableClass = PsiUtil.resolveClassInClassTypeOnly(iterableType)

                        val list = ArrayList<Any>()
                        val classTypeName = iterableClass!!.name!!
                        if (MyPsiUtil.isNormalType(classTypeName)) {
                            MyPsiUtil.normalTypes[classTypeName]?.let { list.add(it) }
                        } else {
                            if (iterableType != null) {
                                list.add((argToJsonObj(iterableType)))
                            }
                        }
                        kv[name] = list
                    }
                    //map type
                    else if (MyPsiUtil.isMap(type)) {
                        kv[name] = mapOf("key" to "value")
                    }
                    //enum
                    else if (MyPsiUtil.isEnum(type)) {
                        val fieldList = PsiUtil.resolveClassInClassTypeOnly(type)!!.fields
                        var value = ""
                        if (!fieldList.isNullOrEmpty()) {
                            if (fieldList[0] is PsiEnumConstant) {
                                value = fieldList[0].name
                            }
                        }
                        kv[name] = value
                    } else {
                        kv[name] = classToJson(PsiUtil.resolveClassInType(type))
                    }
                }
            }
        }
        return kv
    }


    @JvmStatic
    fun argToJsonObj(psiType: PsiType): Any {
        val list = ArrayList<Any>()

        if (psiType is PsiPrimitiveType) {
            return PsiTypesUtil.getDefaultValue(psiType)
        }

        //reference Type
        val fieldTypeName = psiType.presentableText
        //normal Type
        if (MyPsiUtil.isNormalType(fieldTypeName)) {
            return if ("String" == fieldTypeName) {
                ""
            } else {
                MyPsiUtil.normalTypes[fieldTypeName] ?: ""
            }
        }
        //array type
        else if (MyPsiUtil.isArray(psiType)) {
            val deepType = psiType.deepComponentType
            val deepTypeName = deepType.presentableText
            if (deepType is PsiPrimitiveType) {
                list.add(PsiTypesUtil.getDefaultValue(deepType))
            } else if (MyPsiUtil.isNormalType(deepTypeName)) {
                MyPsiUtil.normalTypes[deepTypeName]?.let { list.add(it) }
            } else {
                list.add(classToJson(PsiUtil.resolveClassInType(deepType)))
            }
        }
        //list type
        else if (MyPsiUtil.isList(psiType)) {
            val iterableType = PsiUtil.extractIterableTypeParameter(psiType, false)
            val iterableClass = PsiUtil.resolveClassInClassTypeOnly(iterableType)
            val classTypeName = iterableClass!!.name!!
            if (MyPsiUtil.isNormalType(classTypeName)) {
                MyPsiUtil.normalTypes[classTypeName]?.let { list.add(it) }
            } else {
                list.add(classToJson(iterableClass))
            }
        }
        //map type
        else if (MyPsiUtil.isMap(psiType)) {
            return mapOf("key" to "value")
        }                 //primitive Type
        else {
            return classToJson(PsiUtil.resolveClassInType(psiType))
        }
        return list
    }

}