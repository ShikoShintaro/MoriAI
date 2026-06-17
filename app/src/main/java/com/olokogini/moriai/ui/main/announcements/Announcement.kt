package com.olokogini.moriai.ui.main.announcements

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
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import com.olokogini.moriai.ui.components.PriorityBadge
import com.olokogini.moriai.ui.components.formatDate

@Composable
fun AnnouncementScreen() {
    var announcements by remember { mutableStateOf<List<Event>>(emptyList()) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {

        while (true) {
            try {

                EventPoller.checkForNewEvents(context)

                val response = RetroFitClient.api.getLatestEvents()

                if (response.isSuccessful) {
                    val body = response.body()
                    val events = body?.events ?: emptyList()

                    announcements = events.filter {
                        it.topic == "announcement"
                    }
                }
            } catch (e: Exception) {
                println("Error fetching announcements: ${e.message}")
            }
            kotlinx.coroutines.delay(10000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Announcements",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (announcements.isEmpty()) {
            Text("No announcement yet.")
        } else {
            androidx.compose.foundation.lazy.LazyColumn {
                items(announcements) { item ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
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

                            // Message box
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Text(
                                    text = item.message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Badges
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
                                        color = MaterialTheme.colorScheme.secondaryContainer,
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