package com.olokogini.moriai.ui.main.profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.platform.LocalContext
import com.olokogini.moriai.api.GetProfileRequest

import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.UpdateProfileRequest

import kotlinx.coroutines.launch



@Composable
fun ProfileScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var imageUrl by remember { mutableStateOf("") }

    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val userEmail = prefs.getString("email", "") ?: ""

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        if (uri != null) {

            ProfileUploadHelper.uploadImage(
                context,
                uri,
                object : ProfileUploadHelper.UploadCallback {

                    override fun onSuccess(uploadedUrl: String) {
                        scope.launch {
                            try {
                                val response = RetroFitClient.api.updateProfileImage(
                                    UpdateProfileRequest(userEmail, uploadedUrl)
                                )

                                if (response.isSuccessful) {
                                    println("Saved to DB")
                                } else {
                                    println("Save failed")
                                }

                            } catch (e: Exception) {
                                println("Error: ${e.message}")
                            }
                        }
                        imageUrl = uploadedUrl
                    }

                    override fun onError(error: String) {
                        println("Upload error: $error")
                    }
                }
            )
        }
    }

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            try {
                val response = RetroFitClient.api.getProfile(
                    GetProfileRequest(userEmail)
                )

                if (response.isSuccessful && response.body() != null) {
                    val url = response.body()!!.imageUrl

                    println("LOADED IMAGE URL: $url")

                    imageUrl = url
                } else {
                    println("Failed to load profile")
                }
            } catch (e: Exception) {
                println("Error loading profile : ${e.message}")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Profile", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        if (imageUrl.isNotEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        } else {
            Text("No Image")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                launcher.launch("image/*")
            }
        ) {
            Text("Change Profile Picture")
        }
    }
}