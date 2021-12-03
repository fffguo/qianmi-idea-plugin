package com.github.qianmi.util

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration


object HttpUtil {

    private var httpClient = HttpClient.newHttpClient()

    @JvmStatic
    fun postJson(url: String, body: String): HttpResponse<String> {

        val request = HttpRequest.newBuilder()
            .uri(URI(url))
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .header("Content-Type", "application/json")
            .timeout(Duration.ofSeconds(3))
            .build()

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }

    @JvmStatic
    fun get(url: String, cookie: String): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .uri(URI(url))
            .GET()
            .header("Cookie", cookie)
            .build()

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }


    @JvmStatic
    fun putJson(url: String, body: String, cookie: String): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .uri(URI(url))
            .PUT(HttpRequest.BodyPublishers.ofString(body))
            .header("Content-Type", "application/json")
            .header("Cookie", cookie)
            .build()

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }

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
