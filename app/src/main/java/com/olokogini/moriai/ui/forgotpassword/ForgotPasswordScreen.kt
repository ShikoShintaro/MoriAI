package com.olokogini.moriai.ui.forgotpassword

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.navigation.NavController


@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("ForgotPassword", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email = it },
            label = { Text("Enter your email") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // TODO : send email otp backend will be added soon / OTP
        }) {
            Text("Send Reset Link")
        }

        TextButton(onClick = {
            navController.navigate("login")
        }) {}
            Text("Back to login")
    }
}