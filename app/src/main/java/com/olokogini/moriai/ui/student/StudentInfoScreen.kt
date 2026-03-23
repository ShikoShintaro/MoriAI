package com.olokogini.moriai.ui.student

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.StudentInfoRequest

@Composable
fun StudentInfoScreen(navController: NavController, email: String) {
    var name by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    var message by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Student Information", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = name, onValueChange = { name = it}, label = { Text("Full Name")})
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = course, onValueChange = { course = it}, label = { Text("Course")})
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = birthdate, onValueChange = { birthdate = it}, label = { Text("Birthdate")})
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = section, onValueChange = { section = it}, label = { Text("Section")})
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = year, onValueChange = { year = it}, label = { Text("Year")})
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        val response = RetroFitClient.api.submitStudentInfo(
                            StudentInfoRequest(
                                email = email,
                                fullName = name,
                                course = course,
                                birthdate = birthdate,
                                section = section,
                                year = year
                            )
                        )
                        if (response.isSuccessful) {
                            message = "Saved Sucessfully"
                            navController.navigate("login")
                        } else {
                            message = "Failed to save"
                        }
                    } catch (e: Exception) {
                        message = "Error : ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(message)

    }
}