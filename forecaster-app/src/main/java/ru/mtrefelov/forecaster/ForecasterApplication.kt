package ru.mtrefelov.forecaster

import android.app.Application
import timber.log.Timber

class ForecasterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            val debugTree = Timber.DebugTree()
            Timber.plant(debugTree)
        }
    }
}