package com.olokogini.moriai.api

import retrofit2.http.Body
import retrofit2.http.POST

data class ChatRequest(
    val message: String
)

data class ChatResponse(
    val reply: String
)

interface ChatService {

    @POST("chat") // matches your backend route
    suspend fun sendMessage(
        @Body request: ChatRequest
    ): ChatResponse
}