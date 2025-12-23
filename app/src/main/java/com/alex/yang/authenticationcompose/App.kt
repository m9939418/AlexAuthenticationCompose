package com.alex.yang.authenticationcompose

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by AlexYang on 2025/12/18.
 *
 *
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        FacebookSdk.setAutoInitEnabled(true)
        FacebookSdk.fullyInitialize()
        AppEventsLogger.activateApp(this)
    }
}

const val TAG = "DEBUG"