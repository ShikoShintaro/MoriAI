package com.olokogini.moriai.ui.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.verifyResetRequest
import kotlinx.coroutines.launch

@Composable
fun RestOtpScreen(
    navController: NavController,
    email : String
) {
    var code by remember { mutableStateOf("") }
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
                text = "RESET VERIFICATION",
                fontSize = 12.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card (
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text (
                        text = "ENTER CODE",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF42A5F5)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = code,
                        onValueChange = {
                            if (it.length <= 6 && it.all { ch -> ch.isDigit() }) {
                                code = it
                            }
                        },
                        label = { Text("6-Digit Code") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {

                            scope.launch {

                                isLoading = true
                                error = ""

                                try {

                                    val response = RetroFitClient.api.verifyResetOtp(
                                        verifyResetRequest(
                                            email = email,
                                            code = code.trim()
                                        )
                                    )

                                    if (response.isSuccessful) {

                                        navController.navigate("reset_password/$email") {
                                            popUpTo("reset_otp/$email") {
                                                inclusive = true
                                            }
                                        }

                                    } else {
                                        error = "Invalid or expired code"
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

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),

                        contentPadding = PaddingValues(),

                        enabled = code.length == 6 && !isLoading
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
                                    color = Color.Black
                                )
                            } else {
                                Text(
                                    text = "VERIFY",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                    }

                    if (error.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}