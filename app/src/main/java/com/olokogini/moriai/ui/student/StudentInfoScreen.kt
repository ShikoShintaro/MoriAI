package com.olokogini.moriai.ui.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.api.StudentInfoRequest
import kotlinx.coroutines.launch

@Composable
fun StudentInfoScreen(
    navController: NavController,
    email : String
) {
    var name by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE3F2FD),
            Color(0xFFB2DFDB)
        )
    )

    val buttonGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF64B5F6),
            Color(0xFF4DB6AC)
        )
    )

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = "MORI",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF263238)
            )

            Text(
                text = "STUDENT PROFILE SETUP",
                fontSize = 12.sp,
                color = Color.DarkGray
            )

        }
    }

}