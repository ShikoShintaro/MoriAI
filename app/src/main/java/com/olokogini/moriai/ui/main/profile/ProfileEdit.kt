package com.olokogini.moriai.ui.main.profile

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.util.Calendar
import androidx.compose.foundation.background
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.StudentInfoRequest
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    navController: NavHostController
) {

    val context = LocalContext.current

    val prefs = context.getSharedPreferences(
        "user_prefs",
        Context.MODE_PRIVATE
    )

    val email = prefs.getString("email", "") ?: ""

    val scope = rememberCoroutineScope()

    var fullName by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    var courseExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }

    val courses = listOf("ABCom", "ABEng", "BSP", "ABA", "BSAIS", "BSA", "BSMA", "BSREM", "BSIA", "BSLM", "BSBA", "ACT", "BSCS", "BSIT", "BSIS", "BECEd", "BELEMEd", "BSEd", "BTVTEd", "BSHM", "BSTM", "BSC", "BSISM", "BPA", "BSCE", "BSELE", "BSMedTech", "BSN")

    val years = listOf(
        "1st Year",
        "2nd Year",
        "3rd Year",
        "4th Year"
    )

    val calendar = Calendar.getInstance()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            tonalElevation = 10.dp,
            shadowElevation = 12.dp,
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.headlineSmall
                )

                // FULL NAME
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                // BIRTHDATE
                OutlinedButton(
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _, yearValue, month, day ->
                                birthdate = "${month + 1}/$day/$yearValue"
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (birthdate.isBlank()) "Select Birthdate" else birthdate)
                }

                // COURSE DROPDOWN
                ExposedDropdownMenuBox(
                    expanded = courseExpanded,
                    onExpandedChange = { courseExpanded = !courseExpanded }
                ) {

                    OutlinedTextField(
                        value = course,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Course / Program") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(16.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = courseExpanded,
                        onDismissRequest = { courseExpanded = false }
                    ) {
                        courses.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    course = it
                                    courseExpanded = false
                                }
                            )
                        }
                    }
                }

                // SECTION
                OutlinedTextField(
                    value = section,
                    onValueChange = { section = it },
                    label = { Text("Section") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                // YEAR DROPDOWN
                ExposedDropdownMenuBox(
                    expanded = yearExpanded,
                    onExpandedChange = { yearExpanded = !yearExpanded }
                ) {

                    OutlinedTextField(
                        value = year,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Year Level") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(16.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = yearExpanded,
                        onDismissRequest = { yearExpanded = false }
                    ) {
                        years.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    year = it
                                    yearExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            try {

                                val request = StudentInfoRequest(
                                    email = email,
                                    fullName = fullName,
                                    course = course,
                                    birthdate = birthdate,
                                    section = section,
                                    year = year
                                )

                                val response = RetroFitClient.api.updateStudentInfo(request)

                                if (response.isSuccessful) {
                                    println("✅ Student info saved")

                                    navController.popBackStack() // optional: go back
                                } else {
                                    println("❌ Failed: ${response.code()}")
                                }

                            } catch (e: Exception) {
                                println("❌ Error: ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
}