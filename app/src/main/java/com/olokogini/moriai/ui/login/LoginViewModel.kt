package com.olokogini.moriai.ui.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olokogini.moriai.api.LoginRequest
import com.olokogini.moriai.api.RetroFitClient
import kotlinx.coroutines.launch

class LoginViewModel(
    private val context: Context
) : ViewModel() {

    var state by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(value: String) {
        state = state.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        state = state.copy(password = value)
    }

    fun login(onSuccess: () -> Unit) {

        viewModelScope.launch {

            state = state.copy(
                isLoading = true,
                message = "",
                isSuccess = false
            )

            if (state.email.isBlank() || state.password.isBlank()) {
                state = state.copy (
                    isLoading = false,
                    message = "Please enter email and password."
                )
                return@launch
            }

            try {

                val response = RetroFitClient.api.login(
                    LoginRequest(
                        email = state.email.trim(),
                        password = state.password.trim()
                    )
                )

                if (response.isSuccessful) {

                    val body = response.body()

                    if (body?.message == "Login Success") {

                        val emailFromApi = body.email ?: ""

                        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                            .edit()
                            .putBoolean("is_logged_in", true)
                            .putString("email", emailFromApi)
                            .putString("saved_password", state.password)
                            .apply()

                        state = state.copy(
                            isLoading = false,
                            isSuccess = true,
                            message = ""
                        )

                        onSuccess()

                    } else {
                        state = state.copy(
                            isLoading = false,
                            isSuccess = false,
                            message = body?.message ?: "Invalid credentials"
                        )
                    }

                } else {

                    val errorMsg = response.errorBody()?.string()

                    state = state.copy(
                        isLoading = false,
                        message = errorMsg ?: "Server error occurred"
                    )
                }

            } catch (e: Exception) {

                state = state.copy(
                    isLoading = false,
                    message = "Network error: ${e.message}"
                )
            }
        }
    }
}