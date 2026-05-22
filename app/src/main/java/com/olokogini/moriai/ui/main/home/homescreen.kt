package com.olokogini.moriai.ui.main.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    innerNavController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = "Welcome back, Shiko",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your AI companion is ready.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = { innerNavController.navigate("chat") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Chat")
            }

            OutlinedButton(
                onClick = { innerNavController.navigate("profile") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Profile / Stats")
            }

            OutlinedButton(
                onClick = { innerNavController.navigate("settings") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Settings")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MORI System Online",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
