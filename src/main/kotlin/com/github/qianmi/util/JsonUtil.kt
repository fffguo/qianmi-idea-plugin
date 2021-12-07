package com.github.qianmi.util

import com.google.gson.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


object JsonUtil {

    private val gson = GsonBuilder().create()
    private val jsonParser = JsonParser()

    /**
     * 美观json
     */
    @JvmStatic
    fun prettyJson(jsonObj: Any): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(jsonObj) ?: ""
    }

    /**
     * 压缩json
     */
    @JvmStatic
    fun zipPrettyJson(jsonStr: String?): String? {
        return try {
            gson.toJson(jsonParser.parse(jsonStr))
        } catch (_: Exception) {
            null
        }
    }

    fun String.isJsonString(): Boolean {
        return try {
            val json = jsonParser.parse(this)
            json.isJsonObject || json.isJsonArray || json.isJsonNull || json.isJsonPrimitive
        } catch (_: Exception) {
            false
        }
    }

    fun Any.toJsonString(): String {
        return try {
            return gson.toJson(this)
        } catch (_: Exception) {
            ""
        }
    }

    inline fun <reified T> String.toBean(): T? {
        return try {
            Gson().fromJson(this, T::class.java)
        } catch (_: Exception) {
            null
        }
    }

    inline fun <reified T> String.toList(): List<T> {
        return try {
            Gson().fromJson(this, ParameterizedTypeImpl(T::class.java))
        } catch (_: Exception) {
            listOf()
        }
    }

    inline fun <reified T> String.toMutableList(): MutableList<T> {
        return try {
            Gson().fromJson(this, ParameterizedTypeImpl(T::class.java))
        } catch (_: Exception) {
            mutableListOf()
        }
    }

    @JvmStatic
    fun parse(jsonStr: String?): JsonElement {
        return try {
            return jsonParser.parse(jsonStr)
        } catch (_: Exception) {
            JsonNull()
        }
    }

    class ParameterizedTypeImpl(private val c: Class<*>) : ParameterizedType {
        override fun getRawType(): Type = List::class.java

        override fun getOwnerType(): Type? = null

        override fun getActualTypeArguments(): Array<Type> = arrayOf(c)
    }
}