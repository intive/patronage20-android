package com.intive.patronage.smarthome.navigator

import android.os.Bundle
import com.intive.patronage.smarthome.MainActivity

class SplashScreenCoordinator(private val navigator: Navigator, private val bundle: Bundle?) {

    fun goToMainScreen() {
        navigator.goToScreen(ActivityEvent(MainActivity::class.java, bundle))
    }

    fun goBack() {
        navigator.goBack()
    }

    fun close() {
        navigator.close()
    }
}