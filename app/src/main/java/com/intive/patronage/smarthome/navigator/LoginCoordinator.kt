package com.intive.patronage.smarthome.navigator

import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.login.LoginActivity
import com.intive.patronage.smarthome.feature.splashcreen.SplashScreenActivity

class LoginCoordinator(private val navigator: Navigator) {

    fun goToSplashScreen() {
        navigator.goToScreen(ActivityEvent(SplashScreenActivity::class.java))
        navigator.close()
    }

    fun goToMainScreen() {
        navigator.goToScreen(ActivityEvent(SmartHomeActivity::class.java))
        navigator.close()
    }

    fun goToLoginScreen() {
        navigator.goToScreen(ActivityEvent(LoginActivity::class.java))
        navigator.close()
    }

    fun goToBack() {
        navigator.goBack()
    }
}