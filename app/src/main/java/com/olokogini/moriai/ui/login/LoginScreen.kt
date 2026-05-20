package com.olokogini.moriai.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegister: () -> Unit,
    onForgot: () -> Unit
) {

    val colors = MaterialTheme.colorScheme

    // SAME BACKGROUND AS ALL AUTH SCREENS (FIXED CONSISTENCY)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TITLE
            Text(
                text = "MORI",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground
            )

            Text(
                text = "SCHOOL AI COMPANION CHATBOT",
                fontSize = 11.sp,
                color = colors.onSurface.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface
                )
            ) {

                Column(modifier = Modifier.padding(24.dp)) {

                    // HEADER
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "LOG IN",
                            color = colors.primary,
                            fontWeight = FontWeight.Bold
                        )

                        TextButton(onClick = onRegister) {
                            Text(
                                text = "SIGN UP",
                                color = colors.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // EMAIL
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = onEmailChange,
                        placeholder = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // PASSWORD
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = onPasswordChange,
                        placeholder = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    // FORGOT PASSWORD
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onForgot) {
                            Text("Forgot Password?")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // LOGIN BUTTON
                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color.Transparent
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(buttonGradient),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "LOG IN",
                                color = colors.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // ERROR MESSAGE
                    if (state.message.isNotEmpty()) {
                        Text(
                            text = state.message,
                            color = colors.error
                        )
                    }
                }
            }
        }
    }
}