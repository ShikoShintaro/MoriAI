package com.olokogini.moriai.ui.main.event

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.olokogini.moriai.api.Event
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.ui.components.PriorityBadge
import com.olokogini.moriai.ui.components.formatDate
import kotlinx.coroutines.delay

@Composable
fun EventsScreen() {

    var events by remember { mutableStateOf<List<Event>>(emptyList()) }

    LaunchedEffect(Unit) {

        while (true) {
            try {
                val response = RetroFitClient.api.getLatestEvents()

                if (response.isSuccessful) {
                    val body = response.body()?.events ?: emptyList()

                    events = body.filter {
                        it.topic == "event"
                    }
                }

            } catch (e: Exception) {
                println("Events error: ${e.message}")
            }

            delay(10_000) // refresh every 10 seconds
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Events & Schedule",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (events.isEmpty()) {
            Text("No events yet.")
        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(events) { item ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            // Title + Date
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {

                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = formatDate(item.createdAt),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Message Box
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.25f),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Text(
                                    text = item.message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Topic + Priority
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Surface(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = MaterialTheme.shapes.small
                                    ) {
                                        Text(
                                            text = item.topic.uppercase(),
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }

                                    PriorityBadge(item.priority)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}