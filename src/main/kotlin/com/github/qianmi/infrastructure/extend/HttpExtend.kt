package com.github.qianmi.infrastructure.extend

import java.net.http.HttpResponse

object HttpExtend {

    fun HttpResponse<String>.getCookie(key: String): String {
        val map = listOf(
            this.headers().firstValue("Cookie"),
            this.headers().firstValue("cookie"),
            this.headers().firstValue("set-cookie")
        ).filter { header -> header.isPresent }
            .flatMap { header -> header.get().split(";").toList() }
            .map { keyValue -> keyValue.trim().split("=") }
            .associateBy(
                { split -> split[0] },
                { split ->
                    if (split.size >= 2) {
                        split[1]
                    } else {
                        ""
                    }
                }
            )
        return map.getOrDefault(key, "")
    }

    fun HttpResponse<String>.isOk(): Boolean {
        return this.statusCode() == 200
    }
}