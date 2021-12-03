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
    fun put(url: String, body: String, cookie: String): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .uri(URI(url))
            .PUT(HttpRequest.BodyPublishers.ofString(body))
            .header("Cookie", cookie)
            .build()

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }

    fun HttpResponse<String>.getCookie(key: String): String {
        return this.headers().firstValue(key).get()
    }

    fun HttpResponse<String>.isOk(): Boolean {
        return this.statusCode() == 200
    }

}
