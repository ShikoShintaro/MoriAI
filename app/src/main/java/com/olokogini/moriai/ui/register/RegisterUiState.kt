package com.olokogini.moriai.ui.register

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",

    val isLoading: Boolean = false,
    val message: String = "",
    val status: String? = null
)