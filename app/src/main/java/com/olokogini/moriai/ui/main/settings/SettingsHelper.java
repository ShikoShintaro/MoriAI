package com.olokogini.moriai.ui.main.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsHelper {
    private static final String PREF_NAME = "app_settings";

    public static void setDarkMode(Context context, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean("dark_mode", value).apply();
    }

    public static boolean getDarkMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean("dark_mode", false);
    }

    public static void setNotifications(Context context, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean("notifications", value).apply();
    }

    public static boolean getNotifications(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean("notifications", true);
    }

    public static void setAutoSaveChat(Context context, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean("auto_chat", value).apply();
    }

    public static boolean getAutoSaveChat(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean("auto_chat", true);
    }

}
