package com.olokogini.moriai.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olokogini.moriai.api.RegisterRequest
import com.olokogini.moriai.api.RetroFitClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    var state by mutableStateOf(RegisterUiState())
        private set

    fun onUsernameChange(value: String) {
        state = state.copy(username = value)
    }

    fun onEmailChange(value: String) {
        state = state.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        state = state.copy(password = value)
    }

    fun register(
        onNavigateOtp: (String) -> Unit
    ) {

        viewModelScope.launch {

            state = state.copy(
                isLoading = true,
                message = ""
            )

            try {

                val response = RetroFitClient.api.register(
                    RegisterRequest(
                        username = state.username.trim(),
                        email = state.email.trim(),
                        password = state.password.trim()
                    )
                )

                if (response.isSuccessful) {

                    val body = response.body()

                    state = state.copy(
                        isLoading = false,
                        status = body?.status,
                        message = body?.message ?: ""
                    )

                    when (body?.status) {

                        "NEW",
                        "PENDING_OTP" -> {
                            onNavigateOtp(state.email)
                        }
                    }

                } else {

                    val errorBody = response.errorBody()?.string()

                    state = state.copy(
                        isLoading = false,
                        message = when {

                            errorBody?.contains(
                                "already registered and verified",
                                ignoreCase = true
                            ) == true ->
                                "Email already registered and verified"

                            else ->
                                errorBody ?: "Registration failed"
                        }
                    )
                }

            } catch (e: Exception) {

                state = state.copy(
                    isLoading = false,
                    message = e.message ?: "Network Error"
                )
            }
        }
    }

    fun clearMessage() {
        state = state.copy(
            message = ""
        )
    }
}