package com.olokogini.moriai.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun AppDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(title)
        },

        text = {
            Text(message)
        },

        confirmButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("OK")
            }
        }
    )
}