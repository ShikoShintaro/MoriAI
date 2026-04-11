package com.olokogini.moriai.ui.main.chat.data;

import androidx.room.*;
import java.util.List;
import kotlinx.coroutines.flow.Flow;


@Dao
public interface ChatDao {

    @Query("SELECT * FROM chat_message ORDER BY timestamp ASC")
    kotlinx.coroutines.flow.Flow<java.util.List<ChatMessageEntity>> getMessages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(ChatMessageEntity message);

    @Query("DELETE FROM chat_message")
    void clearChat();
}
