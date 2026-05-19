package com.olokogini.moriai.ui.main.chat.data

import com.olokogini.moriai.api.ChatRequest
import com.olokogini.moriai.api.ChatService
import kotlinx.coroutines.flow.Flow

class ChatRepository(
    private val dao: ChatDao,
    private val api: ChatService
) {

    fun getMessages(): Flow<List<ChatMessageEntity>> {
        return dao.getMessages()
    }

    suspend fun sendMessage(message: String) {

        // 1. Save USER message (ONLY HERE)
        dao.insertMessage(
            ChatMessageEntity(
                message = message,
                isUser = true,
                timestamp = System.currentTimeMillis()
            )
        )

        try {
            // 2. Call MORI backend
            val response = api.sendMessage(ChatRequest(message))

            // 3. Save AI reply
            dao.insertMessage(
                ChatMessageEntity(
                    message = response.reply,
                    isUser = false,
                    timestamp = System.currentTimeMillis()
                )
            )

        } catch (e: Exception) {

            dao.insertMessage(
                ChatMessageEntity(
                    message = "MORI is unavailable right now.",
                    isUser = false,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    suspend fun clearChat() {
        dao.clearChat()
    }
}