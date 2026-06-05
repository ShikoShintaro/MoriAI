package com.olokogini.moriai.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olokogini.moriai.ui.components.AppDialog
import androidx.compose.ui.graphics.Color

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

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(colors.background, colors.surface)
    )

    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(colors.primary, colors.secondary)
    )

    LaunchedEffect(state.message, state.isSuccess) {

        if (state.isSuccess) {
            dialogTitle = "Success"
            dialogMessage = "Login successful"
            showDialog = false
            return@LaunchedEffect
        }

        if (state.message.isNotEmpty()) {
            dialogTitle = "Login Failed"
            dialogMessage = state.message
            showDialog = true
        }
    }

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

            Text(
                text = "MORI",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground
            )

            Text(
                text = "SCHOOL AI COMPANION CHATBOT",
                fontSize = 13.sp,
                color = colors.onSurface.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface
                )
            ) {

                Column(modifier = Modifier.padding(24.dp)) {

                    Text(
                        text = "LOG IN",
                        color = colors.primary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = state.email,
                        onValueChange = onEmailChange,
                        placeholder = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = onPasswordChange,
                        placeholder = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        TextButton(onClick = onForgot) {
                            Text("Forgot Password?")
                        }

                        TextButton(onClick = onRegister) {
                            Text(
                                text = "Don't Have An Account? Register",
                                color = colors.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onLoginClick,
                        enabled = !state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
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

                }
            }
        }
        if (showDialog) {
            AppDialog(
                title = dialogTitle,
                message = dialogMessage,
                onDismiss = { showDialog = false }
            )
        }
    }
}