package com.android.mvvmdemo

import android.app.Application
import android.content.Context

class WeatherApplication : Application() {
    companion object {
        lateinit var context: Context
        const val TOKEN = "QHSvsayKr5u22kL7"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


}