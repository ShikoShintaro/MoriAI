package com.olokogini.moriai.ui.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val message: String = "",
    val isSuccess: Boolean = false
)