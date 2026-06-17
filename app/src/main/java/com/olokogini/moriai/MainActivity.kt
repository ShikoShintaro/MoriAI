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
import android.Manifest
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import java.util.concurrent.TimeUnit
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.olokogini.moriai.ui.main.event.EventWorker
import androidx.work.Constraints

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(this)

        val darkMode = SettingsHelper.getDarkMode(this)

        AppCompatDelegate.setDefaultNightMode(
            if (darkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }

        }

        startEventWorker(this)

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

    private fun createNotificationChannel(context: Context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                "mori_events",
                "MORI Notifications",
                android.app.NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Mori AI Event Notifications"
            }

            val manager = context.getSystemService(android.app.NotificationManager::class.java)
            manager.createNotificationChannel(channel)

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

    private fun startEventWorker(context: Context) {

        val workRequest = PeriodicWorkRequestBuilder<EventWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "mori_event_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

}