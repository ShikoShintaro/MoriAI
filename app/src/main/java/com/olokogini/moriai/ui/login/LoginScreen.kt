package com.olokogini.moriai.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import retrofit2.Response
import com.olokogini.moriai.api.ApiResponse
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.olokogini.moriai.api.LoginRequest
import com.olokogini.moriai.api.RetroFitClient
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegister: () -> Unit,
    onForgot: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    try {

                        val response : Response<ApiResponse> = RetroFitClient.api.login(
                            LoginRequest(
                                email = email.trim(),
                                password = password.trim()
                            )
                        )

                        if (response.isSuccessful && response.body() != null) {
                            val emailFromApi = response.body()?.email ?: ""

                            val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                            prefs.edit().putString("email", emailFromApi).apply()

                            println("SAVED EMAIL: $emailFromApi")

                            message = "Login success"

                            onLoginSuccess()
                        } else {
                            message = response.errorBody()?.string() ?: "Login Failed"
                        }

                    } catch (e: Exception) {
                        message = "Error : ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(message)

        TextButton(onClick = onForgot) {
            Text("Forgot Password?")
        }

        TextButton(onClick = onRegister) {
            Text("Create Account")
        }
    }
}