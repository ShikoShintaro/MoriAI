package com.olokogini.moriai.ui.otp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.VerifyRequest
import kotlinx.coroutines.launch

@Composable
fun OtpScreen(navController: NavController, email: String) {
    var code by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Enter Verification Code", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = code,
            onValueChange = {
                if (it.length <= 6) code = it
            },
            label = { Text("6-Digit code") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer( modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        val response = RetroFitClient.api.verify(
                            VerifyRequest(email, code.trim())
                        )

                        if (response.isSuccessful) {
                            println("Verified")

                            navController.navigate("student_info/$email") {
                                popUpTo("otp/$email") { inclusive = true }
                            }
                        } else {
                            println("ERROR CODE: ${response.code()}")
                            println("ERROR BODY: ${response.errorBody()?.string()}")
                            error = "Invalid code or expired"
                        }
                    } catch (e: Exception) {
                        error = "Network error : ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify")
        }
    }
}