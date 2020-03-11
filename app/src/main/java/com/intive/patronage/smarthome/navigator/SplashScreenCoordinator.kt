package com.intive.patronage.smarthome.navigator

import com.intive.patronage.smarthome.spashscreen.testActivity


class SplashScreenCoordinator(private val navigator: Navigator) {

    fun goToMainScreen() {
         navigator.goToScreen(ActivityEvent(testActivity::class.java))
        navigator.close()
    }
}
