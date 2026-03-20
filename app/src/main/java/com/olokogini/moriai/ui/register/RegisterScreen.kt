package com.olokogini.moriai.ui.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.*
import androidx.compose.material3.*
import androidx.compose.ui.text.input.*
import com.olokogini.moriai.api.RegisterRequest
import com.olokogini.moriai.api.RetroFitClient
import kotlinx.coroutines.*

@Composable
fun RegisterScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create Account", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            scope.launch {
                try {
                    val response = RetroFitClient.api.register(
                        RegisterRequest(username, email, password)
                    )

                    if (response.isSuccessful) {
                        println("Success ${response.body()?.message}")
                        navController.navigate("otp")
                    } else {
                        println("Error : ${response.errorBody()?.string()}")
                    }
                } catch(e: Exception) {
                    println("EXCEPTION ERROR : ${e.message}")
                }
            }
        }) {
            Text("Register")
        }

        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text("Already have an account?")
        }
    }
}