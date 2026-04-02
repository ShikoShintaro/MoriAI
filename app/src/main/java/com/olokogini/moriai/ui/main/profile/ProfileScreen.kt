package com.olokogini.moriai.ui.main.profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.platform.LocalContext
import com.olokogini.moriai.api.ProfileResponse

import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.UpdateProfileRequest

import kotlinx.coroutines.launch



@Composable
fun ProfileScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var imageUrl by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val userEmail = prefs.getString("email", "") ?: ""
    val hasLoaded = remember { mutableStateOf(false) }

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

    if (userEmail.isNotEmpty() && !hasLoaded.value) {
        hasLoaded.value = true

        ProfileGetHelper.getProfile(
            userEmail,
            object : ProfileGetHelper.CallbackListener {

                override fun onSuccess(profile: ProfileResponse?) {
                    println("PROFILE RESPONSE: $profile") // DEBUG

                    if (profile != null) {
                        imageUrl = profile.imageUrl ?: ""
                        fullName = profile.fullName ?: ""
                        course = profile.course ?: ""
                        section = profile.section ?: ""
                        year = profile.year ?: ""
                    }
                }

                override fun onError(error: String) {
                    println("PROFILE ERROR: $error")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Profile", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        if (imageUrl.isNotEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
            )
        } else {
            Text("No Image")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Name: $fullName")
                Text("Course: $course")
                Text("Section: $section")
                Text("Year: $year")
            }

        }

        Spacer (modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                launcher.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Profile Picture")
        }
    }
}