package com.olokogini.moriai.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PriorityBadge(priority: String) {
    val (bg, textColor) = when (priority) {
       "high" -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.onErrorContainer
        "medium" -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        "low" -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        color = bg,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = priority.uppercase(),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp ),
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }

}