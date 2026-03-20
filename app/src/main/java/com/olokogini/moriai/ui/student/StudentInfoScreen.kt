package com.olokogini.moriai.ui.student

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.navigation.NavController

@Composable
fun StudentInfoScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

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
                //temp
                navController.navigate("login")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

    }
}