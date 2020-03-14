package com.intive.patronage.smarthome.navigator

import com.intive.patronage.smarthome.dashboard.view.SmartHomeActivity


class SplashScreenCoordinator(private val navigator: Navigator) {

    fun goToMainScreen() {
        navigator.goToScreen(ActivityEvent(SmartHomeActivity::class.java))
        navigator.close()
    }
}