package com.olokogini.moriai.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.Call

data class LoginRequest(
    val email : String,
    val password : String
)
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

data class StudentInfoRequest (
    val email : String,
    val fullName : String,
    val course : String,
    val birthdate : String,
    val section : String,
    val year : String
)

data class ApiResponse(
    val message: String,
    val email: String? = null
)

data class UpdateProfileRequest(
    val email : String,
    val imageUrl : String
)

data class GetProfileRequest(
    val email : String
)

data class ProfileResponse(
    val imageUrl: String,
    val fullName: String,
    val course: String,
    val section: String,
    val year: String,
    val birthdate: String
)

data class UploadResponse(
    val imageUrl: String
)


interface AuthService{

    @POST("auth/login")
    suspend fun login(
        @Body request : LoginRequest
    ): Response<ApiResponse>

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

    @POST("auth/student-info")
    suspend fun submitStudentInfo(
        @Body request: StudentInfoRequest
    ): Response<ApiResponse>

    @POST("auth/update-profile-image")
    suspend fun updateProfileImage(
        @Body request : UpdateProfileRequest
    ): Response<ApiResponse>

    @POST("auth/get-profile")
    fun getProfile(
        @Body request : GetProfileRequest
    ): Call<ProfileResponse>

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<UploadResponse>

}