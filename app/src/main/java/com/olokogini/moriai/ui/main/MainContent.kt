package com.olokogini.moriai.ui.main

import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.olokogini.moriai.ui.main.chat.ChatScreen
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
            ChatScreen()
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