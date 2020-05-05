package com.intive.patronage.smarthome

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.intive.patronage.smarthome.common.DARK_MODE_KEY
import com.intive.patronage.smarthome.common.PreferencesWrapper
import com.intive.patronage.smarthome.di.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.parameter.parametersOf

class SmartHomeApplication : Application() {

    private val preferences: PreferencesWrapper by inject {
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
                smartHomeActivityModule,
                homeApiModule,
                preferencesModule
            )
        }
        setInitialTheme()
    }

    private fun setInitialTheme() {
        if (!preferences.checkIfContains(DARK_MODE_KEY)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            if (preferences.getBooleanFromPreference(DARK_MODE_KEY)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}