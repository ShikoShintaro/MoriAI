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

class LoginViewModel (
    private val context : Context
) : ViewModel() {
    var state by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(value : String) {
        state = state.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        state = state.copy(password = value)
    }

    fun login(onSuccess : () -> Unit) {

        viewModelScope.launch {

            try {
                val response = RetroFitClient.api.login(
                    LoginRequest(
                        email = state.email.trim(),
                        password = state.password.trim()
                    )
                )

                if (response.isSuccessful && response.body() != null) {
                    val emailFromApi = response.body()?.email ?: ""

                    val prefs = context.getSharedPreferences(
                        "user_prefs",
                        Context.MODE_PRIVATE
                    )

                    prefs.edit()
                        .putBoolean("is_logged_in", true)
                        .putString("email", emailFromApi)
                        .apply()

                    state = state.copy(message = "Login Success")

                    onSuccess()
                } else {
                    state = state.copy(message = "Login Failed")
                }

            } catch (e: Exception) {
                state = state.copy(message = "Error : ${e.message}")
            }

        }
    }

}