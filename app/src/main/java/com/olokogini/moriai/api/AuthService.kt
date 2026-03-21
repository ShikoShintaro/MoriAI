package com.olokogini.moriai.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class VerifyRequest (
    val email : String,
    val code : String
)

data class ForgotPasswordRequest (
    val email: String
)

data class verifyResetRequest (
    val email: String,
    val code : String
)

data class resetPasswordRequest (
    val email : String,
    val newPassword : String
)

data class ApiResponse(
    val message: String
)

interface AuthService{

    @POST("auth/verify")
    suspend fun verify(
        @Body request: VerifyRequest
    ): Response<ApiResponse>

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<ApiResponse>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): Response<ApiResponse>

    @POST("auth/verify-reset")
    suspend fun verifyResetOtp(
        @Body request: verifyResetRequest
    ): Response<ApiResponse>

    @POST("auth/reset-password")
    suspend fun resetPassword (
        @Body request: resetPasswordRequest
    ): Response<ApiResponse>
}