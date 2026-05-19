package com.olokogini.moriai.ui.forgotpassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.resetPasswordRequest
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    email: String
) {

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember {  mutableStateOf("") }

    var error by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE3F2FD),
            Color(0xFFB2DFDB)
        )
    )

    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF64B5F6),
            Color(0xFF4DB6AC)
        )
    )

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            Text (
                text = "MORI",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF263238)
            )

            Text (
                text = "PASSWORD RESET",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card (
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text (
                        text = "RESET PASSWORD",
                        fontWeight = FontWeight.Bold,
                         color = Color(0xFF42A5F5)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = {
                            Text("New Password")
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                        },
                        label = {
                            Text("Confirm Password")
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (password.length < 6) {
                                error = "Password must be at least 6 characters"
                                return@Button
                            }

                            if (password != confirmPassword) {
                                error = "Password do not match"
                                return@Button
                            }

                            scope.launch {
                                isLoading = true
                                error = ""

                                try {

                                    val response = RetroFitClient.api.resetPassword(
                                        resetPasswordRequest(
                                            email,
                                            password
                                        )
                                    )

                                    if (response.isSuccessful) {
                                        navController.navigate("login") {
                                            popUpTo("login") {
                                                inclusive = true
                                            }
                                        }
                                    } else {
                                        error = "Reset failed"
                                    }

                                } catch (e: Exception) {
                                        error = "Network error : ${e.message}"
                                }

                                isLoading = true
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(),

                        enabled = password.isNotEmpty() &&
                        confirmPassword.isNotEmpty() &&
                        !isLoading
                    ) {

                        Box (
                            modifier = Modifier
                                .fillMaxSize()
                                .background(buttonGradient),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.Black
                                )
                            } else {
                                Text (
                                    text = "RESET PASSWORD",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                    }

                    if (error.isNotEmpty()) {
                         Spacer(modifier = Modifier.height(12.dp))

                        Text (
                            text = "error",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                }
            }

        }

    }

}
