package com.olokogini.moriai.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.content.Context
import androidx.compose.runtime.mutableStateOf
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

            state = state.copy(isLoading = true, message = "")

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
                            .apply()

                        state = state.copy(
                            isLoading = false,
                            message = "Login Success"
                        )

                        onSuccess()

                    } else {
                        state = state.copy(
                            isLoading = false,
                            message = body?.message ?: "Login failed"
                        )
                    }

                } else {

                    val errorMsg = response.errorBody()?.string()

                    state = state.copy(
                        isLoading = false,
                        message = errorMsg ?: "Login Failed"
                    )
                }

            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    message = "Network Error: ${e.message}"
                )
            }
        }
    }
}