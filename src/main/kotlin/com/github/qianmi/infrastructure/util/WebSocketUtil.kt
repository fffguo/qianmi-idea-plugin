package com.github.qianmi.infrastructure.util

import java.net.http.HttpClient
import java.net.http.WebSocket


object WebSocketUtil {

    @JvmStatic
    var builder: WebSocket.Builder = HttpClient.newHttpClient().newWebSocketBuilder()

}
