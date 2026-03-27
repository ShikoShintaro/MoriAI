package com.olokogini.moriai.ui.main.chat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ChatScreen() {
    var input by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            messages.forEach { msg ->
                Text(
                    text = if (msg.isUser) "You : ${msg.text}" else "MORI: ${msg.text}"
                )
                Spacer( modifier = Modifier.height(8.dp))
            }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type message...") }
            )

            Spacer( modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (input.isNotBlank()) {
                        messages.add(ChatMessage(input, true))
                        input = ""

                        messages.add(ChatMessage("Thinking..", false))
                    }
                }
            ) {
                Text("Send")
            }

        }



    }
}