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
import androidx.compose.foundation.background

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
        modifier = Modifier.fillMaxSize()
    ) {

        // Cover + Profile Picture
        Box {

            // Cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )

            // Profile Image (overlapping)
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = 70.dp)
            ) {
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    border = ButtonDefaults.outlinedButtonBorder,
                    tonalElevation = 4.dp
                ) {
                    if (imageUrl.isNotEmpty()) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(140.dp)
                                .clip(MaterialTheme.shapes.extraLarge)
                        )
                    } else {
                        Box(
                            modifier = Modifier.size(140.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Image")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = fullName,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$course • $section • $year",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Change Photo")
            }

            OutlinedButton(
                onClick = { /* future edit profile */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Edit Profile")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    "Profile Info",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Name: $fullName")
                Text("Course: $course")
                Text("Section: $section")
                Text("Year: $year")
            }
        }
    }
}