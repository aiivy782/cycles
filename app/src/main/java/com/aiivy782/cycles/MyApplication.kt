package com.aiivy782.cycles

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.android.material.color.DynamicColors

class MyApplication: Application() {
    companion object {
        // Статическая переменная для доступа из всех activity
        lateinit var preferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        preferences = PreferenceManager.getDefaultSharedPreferences(this)

        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}