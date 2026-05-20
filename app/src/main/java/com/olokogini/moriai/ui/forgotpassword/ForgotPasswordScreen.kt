package com.olokogini.moriai.ui.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.ForgotPasswordRequest
import kotlinx.coroutines.launch
import java.security.KeyStore

@Composable
fun ForgotPasswordScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val colors = MaterialTheme.colorScheme

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.surface
        )
    )
    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(
            colors.primary,
            colors.secondary
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            // TITLE
            Text(
                text = "MORI",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground
            )

            Text(
                text = "PASSWORD RECOVERY",
                fontSize = 12.sp,
                color = colors.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // CARD
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface
                ),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(modifier = Modifier.padding(24.dp)) {

                    Text(
                        text = "FORGOT PASSWORD",
                        fontWeight = FontWeight.Bold,
                        color = colors.primary
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // EMAIL INPUT
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Enter Your Email") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // BUTTON
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
                                        error = "Failed to send reset code"
                                    }

                                } catch (e: Exception) {
                                    error = "Network error: ${e.message}"
                                }

                                isLoading = false
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        enabled = email.isNotEmpty() && !isLoading
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(buttonGradient),
                            contentAlignment = Alignment.Center
                        ) {

                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = colors.onPrimary
                                )
                            } else {
                                Text(
                                    text = "SEND CODE",
                                    fontWeight = FontWeight.Bold,
                                    color = colors.onPrimary
                                )
                            }
                        }
                    }

                    if (error.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = error,
                            color = colors.error
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    
                    TextButton(
                        onClick = {
                            navController.navigate("login")
                        }
                    ) {
                        Text("Back to login")
                    }
                }
            }
        }
    }
}