package com.olokogini.moriai

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.olokogini.moriai.navigation.AppNavigation
import com.olokogini.moriai.ui.main.settings.SettingsHelper
import com.olokogini.moriai.ui.theme.MoriAITheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val darkMode = SettingsHelper.getDarkMode(this)

        AppCompatDelegate.setDefaultNightMode(
            if (darkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        setContent {
            MoriAITheme(darkTheme = darkMode) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        startDestination = "bootstrap"
                    )
                }
            }
        }
    }

    private fun getStartDestination(context: Context): String {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        val isLoggedIn = prefs.getBoolean("is_logged_in", false)
        val isFirstLaunchDone = prefs.getBoolean("first_launch_done", false)

        return when {
            !isFirstLaunchDone -> "intro"
            isLoggedIn -> "home"
            else -> "login"
        }
    }
}