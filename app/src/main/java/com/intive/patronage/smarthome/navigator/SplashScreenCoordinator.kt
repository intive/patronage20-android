package com.intive.patronage.smarthome.navigator

import com.intive.patronage.smarthome.splashscreen.TestActivity


class SplashScreenCoordinator(private val navigator: Navigator) {

    fun goToMainScreen() {
        navigator.goToScreen(ActivityEvent(TestActivity::class.java))
        navigator.close()
    }
}