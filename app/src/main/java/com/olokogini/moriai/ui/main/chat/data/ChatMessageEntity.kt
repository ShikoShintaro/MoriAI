package com.olokogini.moriai.ui.main.chat.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_message")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val message: String,
    val isUser: Boolean,
    val timestamp: Long
)