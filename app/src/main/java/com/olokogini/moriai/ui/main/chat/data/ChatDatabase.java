package com.olokogini.moriai.ui.main.chat.data;

import androidx.room.*;
@Database(entities = {ChatMessageEntity.class}, version = 1)
public abstract class ChatDatabase extends RoomDatabase {
    public abstract ChatDao chatDao();
}
