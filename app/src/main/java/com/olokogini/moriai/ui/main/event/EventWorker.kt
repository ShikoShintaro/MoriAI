package com.olokogini.moriai.ui.main.event

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.olokogini.moriai.ui.main.announcements.EventPoller

class EventWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            EventPoller.checkForNewEvents(applicationContext)

            Result.success()

        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}