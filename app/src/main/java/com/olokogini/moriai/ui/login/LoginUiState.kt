package com.olokogini.moriai.ui.login

data class LoginUiState(
    val email : String = "",
    val password : String = "",
    val message : String = "",
    val isLoading : Boolean = false
)