package com.olokogini.moriai

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import com.olokogini.moriai.navigation.AppNavigation
import com.olokogini.moriai.ui.main.settings.SettingsHelper
import com.olokogini.moriai.ui.theme.MoriAITheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load saved settings
        val darkMode = SettingsHelper.getDarkMode(this)

        // Apply system night mode
        AppCompatDelegate.setDefaultNightMode(
            if (darkMode)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )

        setContent {

            val startDestination = getStartDestination(this)

            MoriAITheme(darkTheme = darkMode) {
                AppNavigation(startDestination = startDestination)
            }
        }
    }
    private fun getStartDestination(context: Context): String {

        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("is_logged_in", false)

        return if (isLoggedIn) {
            "home"
        } else {
            "login"
        }
    }
}