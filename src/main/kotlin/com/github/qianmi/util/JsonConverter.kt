package com.github.qianmi.util

import com.alibaba.fastjson.JSONObject
import com.github.qianmi.domain.vo.KV
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiEnumConstant
import com.intellij.psi.PsiPrimitiveType
import com.intellij.psi.PsiType
import com.intellij.psi.util.PsiTypesUtil
import com.intellij.psi.util.PsiUtil


object JsonConverter {


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
                            list.add(JSONObject.parse(classToJsonString(PsiUtil.resolveClassInType(deepType))))
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
                                list.add(JSONObject.parse(argToJsonString(iterableType)))
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


    @JvmStatic
    fun argToJsonString(psiType: PsiType): String {
        val list = ArrayList<Any>()
        //array type
        if (MyPsiUtil.isArray(psiType)) {
            val deepType = psiType.deepComponentType
            val deepTypeName = deepType.presentableText
            if (deepType is PsiPrimitiveType) {
                list.add(PsiTypesUtil.getDefaultValue(deepType))
            } else if (MyPsiUtil.isNormalType(deepTypeName)) {
                MyPsiUtil.normalTypes[deepTypeName]?.let { list.add(it) }
            } else {
                list.add(JSONObject.parse(classToJsonString(PsiUtil.resolveClassInType(deepType))))
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
                list.add(JSONObject.parse(classToJsonString(iterableClass)))
            }
        }
        //map type
        else if (MyPsiUtil.isMap(psiType)) {
            return JSONObject.toJSONString(mapOf("key" to "value"))
        } else {
            return classToJsonString(PsiUtil.resolveClassInType(psiType))
        }
        return JSONObject.toJSONString(list)
    }

}