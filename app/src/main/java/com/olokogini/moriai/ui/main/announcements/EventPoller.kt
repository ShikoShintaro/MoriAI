package com.olokogini.moriai.ui.main.announcements

import android.content.Context
import com.olokogini.moriai.api.Event
import com.olokogini.moriai.api.RetroFitClient
import com.olokogini.moriai.ui.main.settings.SettingsHelper
import java.time.Instant

object EventPoller {

    suspend fun checkForNewEvents(context: Context) {
        try {

            val response = RetroFitClient.api.getLatestEvents()
            if (!response.isSuccessful) return

            val events = response.body()?.events ?: emptyList()
            if (events.isEmpty()) return

            val prefs = context.getSharedPreferences(
                "event_prefs",
                Context.MODE_PRIVATE
            )

            val lastSeenId = prefs.getString("last_event_id", null)

            val newestEvent = events.maxByOrNull {
                runCatching { Instant.parse(it.createdAt) }
                    .getOrDefault(Instant.EPOCH)
            } ?: return

            // first run initialization
            if (lastSeenId == null) {
                prefs.edit()
                    .putString("last_event_id", newestEvent._id)
                    .apply()
                return
            }

            if (newestEvent._id == lastSeenId) return

            if (!SettingsHelper.getNotifications(context)) return

            NotificationHelper.showNotification(
                context,
                newestEvent.title,
                newestEvent.message
            )

            prefs.edit()
                .putString("last_event_id", newestEvent._id)
                .apply()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}