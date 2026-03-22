package com.olokogini.moriai.ui.otp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.verifyResetRequest
import kotlinx.coroutines.launch

@Composable
fun ResetOtpScreen(navController: NavController, email: String) {
    var code by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Reset Password verification",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = code,
            onValueChange = {
                if (it.length <= 6 && it.all { ch -> ch.isDigit() }) {
                    code = it
                }
            },
            label = { Text("6-Digit code")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    error = ""

                    try {
                        val response = RetroFitClient.api.verifyResetOtp(
                            verifyResetRequest( email, code.trim())
                        )

                        if (response.isSuccessful) {
                            navController.navigate("reset_password/$email") {
                                popUpTo("reset_otp/$email") {inclusive = true }
                            }
                        } else {
                            error = "Invalid or expired code"
                        }
                    } catch (e: Exception ) {
                        error = "Network error: ${e.message}"
                    }
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = code.length == 6 && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Verify")
            }
        }

        if (error.isNotEmpty()) {
            Spacer( modifier = Modifier.height(10.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }
    }

}