package com.olokogini.moriai.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("mori_settings")

class AppPreferences(private val context: Context) {

    companion object {
        val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    }

    val isFirstLaunch: Flow<Boolean> =
        context.dataStore.data.map {prefs ->
            prefs[FIRST_LAUNCH] ?: true
        }

    suspend fun setFirstLaunchComplete() {
        context.dataStore.edit { prefs ->
            prefs[FIRST_LAUNCH] = false
        }
    }

}