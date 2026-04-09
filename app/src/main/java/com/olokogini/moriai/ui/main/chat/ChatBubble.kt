package com.olokogini.moriai.ui.main.chat

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.scale

@Composable
fun ChatBubble(message: ChatMessage) {
    val isUser = message.isUser

    var visible by remember {mutableStateOf (false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.09f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(200)) +
            slideInHorizontally(
                initialOffsetX = { if (isUser) it else -it }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
        ) {
            Surface(
                modifier = Modifier.scale(scale),

                color = if (isUser)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.surfaceVariant,

                tonalElevation = if (isUser) 2.dp else 1.dp,

                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isUser) 16.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 16.dp
                )
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(12.dp),
                    color = if (isUser)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

}
