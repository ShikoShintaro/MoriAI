package com.olokogini.moriai.ui.components

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatDate(dateString: String): String {
    return try {
        val instant = Instant.parse(dateString)

        DateTimeFormatter.ofPattern("MMM dd, yyyy")
            .withZone(ZoneId.systemDefault())
            .format(instant)

    } catch (e: Exception) {
        dateString
    }
}