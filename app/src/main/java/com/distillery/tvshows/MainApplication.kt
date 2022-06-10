package com.distillery.tvshows

import android.app.Application
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp

/**
 * Main Application
 */
@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        private lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}