package com.olokogini.moriai.ui.main

import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.olokogini.moriai.ui.main.chat.ChatScreen

@Composable
fun MainContent(navController: NavController) {

    val innerNavController = rememberNavController()

    NavHost(
        navController = innerNavController,
        startDestination = "chat"
    ) {
        composable("chat") {
            ChatScreen()
        }

        compo
    }
}