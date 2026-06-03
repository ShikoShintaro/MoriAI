package com.olokogini.moriai.ui.bootstrap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.olokogini.moriai.api.LoginRequest
import com.olokogini.moriai.api.RetroFitClient
import kotlinx.coroutines.delay

@Composable
fun LoginCheckScreen(navController: NavHostController) {

    val context = LocalContext.current
    var status by remember{mutableStateOf("Checking Server. . .")}

    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("user_prefs", 0)
        val email = prefs.getString("email", null)
        val password = prefs.getString("saved_password", null)

        if (email.isNullOrEmpty() || password.isNullOrEmpty()){
            navController.navigate("login") {
                popUpTo(0)
            }
            return@LaunchedEffect
        }

        var attempts = 0
        val maxAttempts = 20

        while (attempts < maxAttempts) {
            try {
                status = "Checking Server. . . ($attempts/${maxAttempts}"

                val response = RetroFitClient.api.login(
                    LoginRequest(email, password)
                )

                if (response.isSuccessful &&
                    response.body()?.message == "Login Success"
                ) {
                    navController.navigate("home") {
                        popUpTo(0)
                    }
                    return@LaunchedEffect
                }

                navController.navigate("login") {
                    popUpTo(0)
                }
                return@LaunchedEffect

            } catch (e: Exception) {

                e.printStackTrace()
                status = "Server offline. Retrying..."

                attempts++
                delay(10000)
            }

            status = "Cannot reach server. . ."
            delay(3000)

            navController.navigate("login") {
                popUpTo(0)
            }

        }

    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(status)
    }


}