package com.intive.patronage.smarthome.navigator

import android.os.Bundle
import com.intive.patronage.smarthome.SplashScreenActivity

class SplashScreenCoordinator(private val navigator: Navigator) {

    fun goToMainScreen() {
        navigator.goToScreen(ActivityEvent(MainActivity::class.java))
        navigator.close()
    }
}
