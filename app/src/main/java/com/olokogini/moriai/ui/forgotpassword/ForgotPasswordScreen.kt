package com.olokogini.moriai.ui.forgotpassword

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.ForgotPasswordRequest
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Forgot Password", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter your email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    error = ""

                    try {
                        val response = RetroFitClient.api.forgotPassword(
                            ForgotPasswordRequest(email)
                        )

                        if (response.isSuccessful) {
                            navController.navigate("reset_otp/$email")
                        } else {
                            error = "Failed to send OTP"
                        }

                    } catch (e: Exception) {
                        error = "Network error: ${e.message}"
                    }

                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotEmpty() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Send Reset Code")
            }
        }

        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text("Back to login")
        }
    }
}