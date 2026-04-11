package com.olokogini.moriai.ui.main.chat.data;

import androidx.room.*;

@Entity(tableName = "chat_message")
public class ChatMessageEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String message;
    public boolean isUser;
    public long timestamp;

    public ChatMessageEntity(String message, boolean isUser, long timestamp) {
        this.message = message;
        this.isUser = isUser;
        this.timestamp = timestamp;
    }

}
