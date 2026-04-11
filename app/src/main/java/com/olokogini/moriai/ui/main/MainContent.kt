package com.olokogini.moriai.ui.main

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.room.Room
import com.olokogini.moriai.ui.main.chat.ChatScreen
import com.olokogini.moriai.ui.main.chat.data.ChatDatabase
import com.olokogini.moriai.ui.main.chat.data.ChatRepository
import com.olokogini.moriai.ui.main.chat.viewmodel.ChatViewModel
import com.olokogini.moriai.ui.main.chat.viewmodel.ChatViewModelFactory
import com.olokogini.moriai.ui.main.event.EventsScreen
import com.olokogini.moriai.ui.main.profile.ProfileScreen
import com.olokogini.moriai.ui.main.settings.SettingsScreen



@Composable
fun MainContent(innerNavController: NavHostController) {

    NavHost(
        navController = innerNavController,
        startDestination = "chat"
    ) {
        composable("chat") {
            val context = LocalContext.current

            val db = Room.databaseBuilder(
                context.applicationContext,
                ChatDatabase::class.java,
                "chat_db"
            ).build()

            val repo = ChatRepository(db.chatDao())

            val viewModel: ChatViewModel = viewModel(
                factory = ChatViewModelFactory(repo)
            )

            ChatScreen(viewModel)
        }

        composable("profile") {
            ProfileScreen()
        }

        composable("settings") {
            SettingsScreen()
        }


        composable("events"){
            EventsScreen()
        }
    }
}