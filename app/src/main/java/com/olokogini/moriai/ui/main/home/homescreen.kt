package com.olokogini.moriai.ui.main.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.olokogini.moriai.api.ProfileResponse
import com.olokogini.moriai.ui.main.profile.ProfileGetHelper

@Composable
fun HomeScreen(
    innerNavController: NavHostController
) {

    val context = LocalContext.current

    val prefs = remember {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    val userEmail = prefs.getString("email", "") ?: ""

    var fullName by remember { mutableStateOf("") }
    val hasLoaded = remember { mutableStateOf(false) }

    // fallback from cache
    val cachedName = prefs.getString("username", "Student") ?: "Student"

    val username = fullName.ifBlank { cachedName }

    if (userEmail.isNotEmpty() && !hasLoaded.value) {
        hasLoaded.value = true

        ProfileGetHelper.getProfile(
            userEmail,
            object : ProfileGetHelper.CallbackListener {

                override fun onSuccess(profile: ProfileResponse?) {
                    if (profile != null) {
                        fullName = profile.fullName ?: ""

                        // optional: cache it for offline use
                        prefs.edit()
                            .putString("username", profile.fullName ?: "")
                            .apply()
                    }
                }

                override fun onError(error: String) {
                    println("HOME PROFILE ERROR: $error")
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {

        HomeBackground()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF020617).copy(alpha = 0.15f),
                            Color(0xFF020617).copy(alpha = 0.75f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = "Welcome back, $username",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBFFBFF)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your AI companion is ready.",
                    fontSize = 16.sp,
                    color = Color(0xFF7FEFFF)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                Button(
                    onClick = { innerNavController.navigate("chat") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Chat")
                }

                OutlinedButton(
                    onClick = { innerNavController.navigate("profile") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Profile / Stats")
                }

                OutlinedButton(
                    onClick = { innerNavController.navigate("settings") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Settings")
                }
            }

            Text(
                text = "MORI System Online",
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}