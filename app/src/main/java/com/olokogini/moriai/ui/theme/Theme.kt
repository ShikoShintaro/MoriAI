package com.olokogini.moriai.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light Theme
private val LightColors = lightColorScheme(
    primary = Color(0xFF4DB6AC),
    secondary = Color(0xFF64B5F6),

    background = Color(0xFFE3F2FD),
    surface = Color.White,

    error = Color(0xFFEF5350),

    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF263238),
    onSurface = Color(0xFF263238),
    onError = Color.White
)

// Dark Theme
private val DarkColors = darkColorScheme(
    primary = Color(0xFF4DB6AC),
    secondary = Color(0xFF64B5F6),

    background = Color(0xFF0F1115),
    surface = Color(0xFF1A1C22),

    error = Color(0xFFEF5350),

    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)

private val MoriTypography = Typography()

@Composable
fun MoriAITheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = MoriTypography,
        content = content
    )
}