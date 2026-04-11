package com.olokogini.moriai.ui.main.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olokogini.moriai.ui.main.chat.ChatMessage
import com.olokogini.moriai.ui.main.chat.data.ChatRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatViewModel(private val repo: ChatRepository) : ViewModel() {
    val messages = repo.getMessages()
        .map { list ->
            list.map {
                ChatMessage(it.message, it.isUser)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    fun sendMessage(text : String, isUser: Boolean) {
        viewModelScope.launch {
            repo.sendMessage(text , isUser)
        }
    }



}
