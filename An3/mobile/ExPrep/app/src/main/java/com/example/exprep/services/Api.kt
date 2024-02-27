package com.example.exprep.services

import okhttp3.OkHttpClient

object Api {
    private val url = "172.22.242.132:3000"
    private val httpUrl = "http://$url/"
    val wsUrl = "ws://$url"
    val tokenInterceptor = TokenInterceptor()
    val okHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(tokenInterceptor)
    }.build()
}