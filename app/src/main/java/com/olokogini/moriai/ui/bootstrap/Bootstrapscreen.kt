package com.olokogini.moriai.ui.bootstrap

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.olokogini.moriai.data.AppPreferences
import kotlinx.coroutines.flow.first

@Composable
fun BootstrapScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefs = remember { AppPreferences(context) }

    LaunchedEffect(Unit) {

        val isFirstLaunch = prefs.isFirstLaunch.first()

        val sharedPrefs = context.getSharedPreferences("user_prefs", 0)
        val isLoggedIn = sharedPrefs.getBoolean("is_logged_in", false)

        println("FIRST LAUNCH = $isFirstLaunch")
        println("IS LOGGED IN = $isLoggedIn")

        val destination = when {
            isLoggedIn -> "login_check"
            isFirstLaunch -> "intro"
            else -> "login"
        }

        navController.navigate(destination) {
            popUpTo("bootstrap") { inclusive = true }
        }
    }
}