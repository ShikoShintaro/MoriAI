package com.olokogini.moriai.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetroFitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val chatApi: ChatService by lazy {
        retrofit.create(ChatService::class.java)
    }
}