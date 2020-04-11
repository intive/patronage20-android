package com.intive.patronage.smarthome

import android.app.Application
import com.intive.patronage.smarthome.di.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.parameter.parametersOf


class SmartHomeApplication() : Application() {

    private val analytics: AnalyticsWrapper by inject {
        parametersOf(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SmartHomeApplication)
            modules(
                splashScreenModule,
                networkApiModule,
                smartHomeAPIModule,
                dashboardRoomModule,
                dashboardModule,
                dashboardApiModule,
                lightsDetailsModule,
                blindDetailsModule,
                viewPagerModule,
                temperatureDetailsModule,
                hvacDetailsModule,
                developerSettingsModule,
                homeModule,
                loginModule,
                analyticsModule,
                smartHomeActivityModule
            )
        }
    }

}