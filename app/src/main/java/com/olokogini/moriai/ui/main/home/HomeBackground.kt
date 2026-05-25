package com.olokogini.moriai.ui.main.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.sin

@Composable
fun HomeBackground() {

    val transition = rememberInfiniteTransition(label = "bg")

    val t by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "t"
    )

    val deep = Color(0xFF04060F)
    val navy = Color(0xFF071A2E)
    val cyan = Color(0xFF00E5FF)
    val teal = Color(0xFF00FFC6)
    val purple = Color(0xFF7C4DFF)

    val x1 = 0.3f + 0.2f * sin(t * 2 * Math.PI).toFloat()
    val y1 = 0.4f + 0.25f * sin((t + 0.25f) * 2 * Math.PI).toFloat()

    val x2 = 0.7f + 0.25f * sin((t + 0.5f) * 2 * Math.PI).toFloat()
    val y2 = 0.6f + 0.2f * sin((t + 0.75f) * 2 * Math.PI).toFloat()

    val glow1 = Brush.radialGradient(
        colors = listOf(
            cyan.copy(alpha = 0.35f),
            cyan.copy(alpha = 0.12f),
            Color.Transparent
        ),
        center = Offset(900f * x1, 1800f * y1),
        radius = 900f
    )

    val glow2 = Brush.radialGradient(
        colors = listOf(
            purple.copy(alpha = 0.25f),
            teal.copy(alpha = 0.10f),
            Color.Transparent
        ),
        center = Offset(900f * x2, 1800f * y2),
        radius = 1000f
    )

    val base = Brush.verticalGradient(
        colors = listOf(
            deep,
            navy,
            deep
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(base)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(glow1)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(glow2)
    )
}