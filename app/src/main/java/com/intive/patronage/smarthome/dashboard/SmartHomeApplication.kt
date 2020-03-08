package com.intive.patronage.smarthome.dashboard

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class SmartHomeApplication() : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SmartHomeApplication)
            modules(dashboardModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}