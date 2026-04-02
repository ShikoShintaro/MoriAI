package com.olokogini.moriai.ui.main.settings

import android.content.Context

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*

@Composable
fun SettingsScreen() {

    val context = LocalContext.current

    var darkMode by remember {
        mutableStateOf(SettingsHelper.getDarkMode(context))
    }

    var notifications by remember {
        mutableStateOf(SettingsHelper.getNotifications(context))
    }

    var autoChat by remember {
        mutableStateOf(SettingsHelper.getAutoSaveChat(context))
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Dark Mode")
            Switch(
                checked = darkMode,
                onCheckedChange = {
                    darkMode = it
                    SettingsHelper.setDarkMode(context, it)
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Notifications")
            Switch(
                checked = notifications,
                onCheckedChange = {
                    notifications = it
                    SettingsHelper.setNotifications(context, it)
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Auto Save Chat")
            Switch(
                checked = autoChat,
                onCheckedChange = {
                    autoChat = it
                    SettingsHelper.setAutoSaveChat(context, it)
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                prefs.edit().clear().apply()

                println("Logged Out")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }

    }

}