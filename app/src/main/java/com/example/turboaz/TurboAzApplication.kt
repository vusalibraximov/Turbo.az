package com.example.turboaz

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TurboAzApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Additional initialization if needed
    }
}
