package com.olokogini.moriai.ui.main.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olokogini.moriai.ui.main.chat.data.ChatRepository

class ChatViewModelFactory(
    private val repo: ChatRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(repo) as T
    }
}