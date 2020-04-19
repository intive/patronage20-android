package com.intive.patronage.smarthome.navigator

import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.login.LoginActivity


class SplashScreenCoordinator(private val navigator: Navigator) {

    fun goToMainScreen() {
        navigator.goToScreen(ActivityEvent(SmartHomeActivity::class.java))
        navigator.close()
    }

    fun goToLoginScreen() {
        navigator.goToScreen(ActivityEvent(LoginActivity::class.java))
        navigator.close()
    }
}