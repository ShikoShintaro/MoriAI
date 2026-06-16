package com.olokogini.moriai.ui.main

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.room.Room
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.ui.main.chat.ChatScreen
import com.olokogini.moriai.ui.main.chat.viewmodel.ChatViewModel
import com.olokogini.moriai.ui.main.chat.viewmodel.ChatViewModelFactory
import com.olokogini.moriai.ui.main.chat.data.ChatRepository
import com.olokogini.moriai.ui.main.chat.data.ChatDatabase
import com.olokogini.moriai.ui.main.event.EventsScreen
import com.olokogini.moriai.ui.main.profile.ProfileScreen
import com.olokogini.moriai.ui.main.settings.SettingsScreen
import com.olokogini.moriai.ui.main.home.HomeScreen
import com.olokogini.moriai.ui.main.profile.ProfileEditScreen


@Composable
fun MainContent(
    innerNavController : NavHostController
) {
    val context = LocalContext.current

    val db = remember {
        Room.databaseBuilder(
            context.applicationContext,
            ChatDatabase::class.java,
            "chat_db"
        ).build()
    }

    val repo = remember {
        ChatRepository(
            dao = db.chatDao(),
            api = RetroFitClient.chatApi
        )
    }

    val viewModel : ChatViewModel = viewModel(
        factory = ChatViewModelFactory(repo)
    )

    NavHost(
        navController = innerNavController,
        startDestination = "chat"
    ) {

        composable("edit_profile") {
            ProfileEditScreen(innerNavController)
        }

        composable("chat"){
            ChatScreen(viewModel = viewModel, navController = innerNavController)
        }

        composable("profile"){
            ProfileScreen(innerNavController)
        }

        composable("settings") {
            SettingsScreen()
        }

        composable("events") {
            EventsScreen()
        }
    }

}