package com.olokogini.moriai.ui.main.chat


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun ChatBubble(
    message: ChatMessage
) {
    val isUser = message.isUser

    val colors = MaterialTheme.colorScheme

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.9f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bubble_scale"
    )

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(250)
        ) + slideInHorizontally(
            initialOffsetX = {
                if (isUser) it else -it
            }
        )
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),

            horizontalArrangement =
                if (isUser) Arrangement.End
                else Arrangement.Start
        ) {

            Surface (
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .scale(scale),

                color =
                    if (isUser)
                        colors.primary
                    else
                        colors.surfaceVariant,

                tonalElevation =
                    if (isUser) 3.dp
                    else 1.dp,

                shape = RoundedCornerShape(
                    topStart = 18.dp,
                    topEnd = 18.dp,
                    bottomStart =
                        if (isUser)
                            18.dp
                        else
                            4.dp,
                    bottomEnd =
                        if (isUser)
                            4.dp
                        else
                            18.dp
                )
            ) {
                Text(
                    text = message.message,

                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 10.dp
                    ),

                    color =
                        if (isUser)
                            colors.onPrimary
                        else
                            colors.onSurfaceVariant,

                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}