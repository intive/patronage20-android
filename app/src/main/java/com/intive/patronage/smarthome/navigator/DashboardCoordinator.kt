package com.intive.patronage.smarthome.navigator

import android.content.Intent
import android.os.Bundle
import com.intive.patronage.smarthome.R

import com.intive.patronage.smarthome.feature.blind.view.BlindDetailsFragment
import com.intive.patronage.smarthome.feature.dashboard.view.DashboardFragment
import com.intive.patronage.smarthome.feature.hvac.view.HvacDetailsFragment
import com.intive.patronage.smarthome.feature.developer_settings.view.DeveloperSettingsActivity
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeFragment
import com.intive.patronage.smarthome.feature.home.view.HomeFragment
import com.intive.patronage.smarthome.feature.light.view.LightsDetailsFragment
import com.intive.patronage.smarthome.feature.login.LoginActivity
import com.intive.patronage.smarthome.feature.settings.view.SettingsFragment
import com.intive.patronage.smarthome.feature.temperature.view.TemperatureDetailsFragment

const val DESTINATION_URL = "destination"
const val DASHBOARD_DESTINATION_URL = "dashboard"
const val HOME_DESTINATION_URL = "home"
const val BLINDS_DESTINATION_URL = "blinds"
const val LIGHT_DESTINATION_URL = "light"
const val HVAC_DESTINATION_URL = "hvac"
const val TEMPERATURE_DESTINATION_URL = "temperature"

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

    fun goToHvacDetalisScreen(bundle: Bundle? = null) {
        navigator.goToScreen(
            FragmentEvent(
                HvacDetailsFragment::class.java,
                bundle,
                R.id.fragment
            )
        )
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

    fun goToHome() {
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

    fun goToSettingsScreen() {
        navigator.goToScreen(FragmentEvent(SettingsFragment::class.java, null, R.id.fragment))
    }

    fun goToLogin() {
        navigator.goToScreen(ActivityEvent(LoginActivity::class.java))
        navigator.close()
    }

    fun goToScreenBasedOnDeeplinkIntent(intent: Intent) {
        when (intent.getStringExtra(DESTINATION_URL)) {
            DASHBOARD_DESTINATION_URL -> goToDashboard()
            HOME_DESTINATION_URL -> goToHome()
            BLINDS_DESTINATION_URL -> goToBlindDetailsScreen(
                intent.extras
            )
            LIGHT_DESTINATION_URL -> goToLightsDetailsScreen(
                intent.extras
            )
            HVAC_DESTINATION_URL -> goToHvacDetalisScreen(
                intent.extras
            )
            TEMPERATURE_DESTINATION_URL -> goToTemperatureDetailsScreen(
                intent.extras
            )
            else -> goToSmartHome()
        }
        intent.removeExtra(DESTINATION_URL)
    }
}