package com.intive.patronage.smarthome.navigator

import android.os.Bundle
import com.intive.patronage.smarthome.R

import com.intive.patronage.smarthome.feature.blind.view.BlindDetailsFragment
import com.intive.patronage.smarthome.feature.dashboard.view.DashboardFragment
import com.intive.patronage.smarthome.feature.hvac.view.HvacDetailsFragment
import com.intive.patronage.smarthome.feature.developer.view.DeveloperSettingsActivity
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeFragment
import com.intive.patronage.smarthome.feature.home.view.HomeFragment
import com.intive.patronage.smarthome.feature.light.view.LightsDetailsFragment
import com.intive.patronage.smarthome.feature.login.LoginActivity
import com.intive.patronage.smarthome.feature.temperature.view.TemperatureDetailsFragment

class DashboardCoordinator(private val navigator: Navigator) {

    fun goToLightsDetailsScreen(bundle: Bundle? = null) {
        navigator.goToScreen(
            FragmentEvent(
                LightsDetailsFragment::class.java,
                bundle,
                R.id.fragment
            )
        )
    }

    fun goToHvacDetalisScreen (bundle: Bundle? = null){
        navigator.goToScreen(
            FragmentEvent(
                HvacDetailsFragment::class.java,
            bundle,
            R.id.fragment
        ))
    }

    fun goToBlindDetailsScreen(bundle: Bundle? = null) {
        navigator.goToScreen(
            FragmentEvent(
                BlindDetailsFragment::class.java,
                bundle,
                R.id.fragment
            )
        )
    }

    fun goToTemperatureDetailsScreen(bundle: Bundle? = null) {
        navigator.goToScreen(
            FragmentEvent(
                TemperatureDetailsFragment::class.java,
                bundle,
                R.id.fragment
            )
        )
    }

    fun goToDashboard() {
        //uncomment and replace Fragment::class
        navigator.goToScreen(FragmentEvent(DashboardFragment::class.java, null, R.id.fragment))
    }

    fun goToHome(){
        navigator.goToScreen(FragmentEvent(HomeFragment::class.java, null, R.id.fragment))
    }

    fun goToSmartHome() {
        navigator.goToScreen(FragmentEvent(SmartHomeFragment::class.java, null, R.id.fragment))
    }

    fun goBack() {
        navigator.goBack()
    }

    fun goToDeveloperSettings() {
        navigator.goToScreen(ActivityEvent(DeveloperSettingsActivity::class.java))
    }

    fun goToLogin() {
        navigator.goToScreen(ActivityEvent(LoginActivity::class.java))
        navigator.close()
    }

}