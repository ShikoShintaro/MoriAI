package com.olokogini.moriai.ui.main.chat.data;

import com.olokogini.moriai.ui.main.chat.ChatMessage;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

public class ChatRepository {
    private ChatDao chatDao;

    public ChatRepository(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    public Flow<List<ChatMessageEntity>> getMessages() {
        return chatDao.getMessages();
    }

    public void sendMessage(String message, boolean isUser) {
        new Thread(() -> {
            ChatMessageEntity msg = new ChatMessageEntity(
                    message,
                    isUser,
                    System.currentTimeMillis()
            );
            chatDao.insertMessage(msg);
        }).start();
    }

    public void clearChat() {
        chatDao.clearChat();
    }

}
