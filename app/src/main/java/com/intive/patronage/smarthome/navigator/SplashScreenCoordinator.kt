package com.intive.patronage.smarthome.navigator


class SplashScreenCoordinator(private val navigator: Navigator) {

    fun goToMainScreen() {
        // navigator.goToScreen(ActivityEvent(MainActivity::class.java))
        navigator.close()
    }
}