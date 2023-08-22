package com.roblescode.tmdb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TMDBApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}