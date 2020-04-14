package com.intive.patronage.smarthome.navigator

import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeFragment
import com.intive.patronage.smarthome.feature.developer.view.DeveloperSettingsActivity
import com.intive.patronage.smarthome.feature.home.view.HomeFragment


class SplashScreenCoordinator(private val navigator: Navigator) {

    fun goToMainScreen() {
        navigator.goToScreen(ActivityEvent(SmartHomeActivity::class.java))
        navigator.close()
    }

    fun goToDeveloperSettings(){
        navigator.goToScreen(ActivityEvent(DeveloperSettingsActivity::class.java))
        navigator.close()
    }

    fun goToHome(){
        navigator.goToScreen(FragmentEvent(HomeFragment::class.java, null, R.id.fragment))
    }
}