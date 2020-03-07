package com.intive.patronage.smarthome.navigator

import android.os.Bundle
import com.intive.patronage.smarthome.MainActivity

class CoordinatorSplashScreen(private val navigator: Navigator, private val bundle: Bundle?) {

    fun goToMainScreen() {
        val activityEvent = ActivityEvent(MainActivity::class.java, bundle)
        navigator.goToScreen(activityEvent)
    }

    fun close() {
        navigator.close()
    }
}