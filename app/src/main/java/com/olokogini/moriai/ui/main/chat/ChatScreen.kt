package com.olokogini.moriai.ui.main.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.olokogini.moriai.ui.main.chat.viewmodel.ChatViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen (
    viewModel: ChatViewModel
) {
    val messages by viewModel.messages.collectAsState(
        initial = emptyList()
    )

    var input by remember { mutableStateOf("") }

    val colors = MaterialTheme.colorScheme

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.surface
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { msg ->
                    ChatBubble(msg)
                }
            }

            HorizontalDivider(
                color = colors.outline.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text("Type message. . .")
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )

                Button(
                    onClick = {
                        if (input.isNotBlank()) {
                            viewModel.sendMessage(input.trim())

                            input = ""
                        }
                    }
                ) {

                    Text(
                        text = "Send",
                        color = colors.onPrimary
                    )

                }

            }

        }
    }

}