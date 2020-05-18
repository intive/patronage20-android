package com.intive.patronage.smarthome.navigator

import android.content.Intent
import android.os.Bundle
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.*

import com.intive.patronage.smarthome.feature.blind.view.BlindDetailsFragment
import com.intive.patronage.smarthome.feature.dashboard.view.DashboardFragment
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.hvac.view.HvacDetailsFragment
import com.intive.patronage.smarthome.feature.developer_settings.view.DeveloperSettingsActivity
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeFragment
import com.intive.patronage.smarthome.feature.home.view.HomeFragment
import com.intive.patronage.smarthome.feature.light.view.LightsDetailsFragment
import com.intive.patronage.smarthome.feature.login.view.LoginActivity
import com.intive.patronage.smarthome.feature.login.view.RegisterActivity
import com.intive.patronage.smarthome.feature.settings.feature.ThirdPartyAcknowledgmentsFragment
import com.intive.patronage.smarthome.feature.settings.view.SettingsFragment
import com.intive.patronage.smarthome.feature.temperature.view.TemperatureDetailsFragment

const val POSITION_HOME_ON_VIEW_PAGER = 1
const val POSITION_HOME_ON_VIEW_PAGER_KEY = "home_position_on_view_pager"

class DashboardCoordinator(private val navigator: Navigator) : DeeplinkCoordinator {

    fun goToLightsDetailsScreen(bundle: Bundle? = null, deeplink: Boolean = false) {
        navigator.goToScreen(
            FragmentEvent(
                LightsDetailsFragment::class.java,
                bundle,
                R.id.fragment
            ),
            deeplink
        )
    }

    fun goToHvacDetalisScreen(bundle: Bundle? = null, deeplink: Boolean = false) {
        navigator.goToScreen(
            FragmentEvent(
                HvacDetailsFragment::class.java,
                bundle,
                R.id.fragment
            ),
            deeplink
        )
    }

    fun goToBlindDetailsScreen(bundle: Bundle? = null, deeplink: Boolean = false) {
        navigator.goToScreen(
            FragmentEvent(
                BlindDetailsFragment::class.java,
                bundle,
                R.id.fragment
            ),
            deeplink
        )
    }

    fun goToTemperatureDetailsScreen(bundle: Bundle? = null, deeplink: Boolean = false) {
        navigator.goToScreen(
            FragmentEvent(
                TemperatureDetailsFragment::class.java,
                bundle,
                R.id.fragment
            ),
            deeplink
        )
    }

    fun goToThirdPartyAcknowledgments(bundle: Bundle? = null) {
        navigator.goToScreen(
            FragmentEvent(
                ThirdPartyAcknowledgmentsFragment::class.java,
                bundle,
                R.id.fragment
            )
        )
    }
    fun goToDashboard(deeplink: Boolean = false) {
        navigator.goToScreen(
            FragmentEvent(SmartHomeFragment::class.java, null, R.id.fragment),
            deeplink
        )
    }

    fun goToHome(bundle: Bundle? = null, deeplink: Boolean = false) {
        navigator.goToScreen(
            FragmentEvent(SmartHomeFragment::class.java, bundle, R.id.fragment),
            deeplink)
    }

    fun goToSmartHome(deeplink: Boolean = false) {
        navigator.goToScreen(
            FragmentEvent(SmartHomeFragment::class.java, null, R.id.fragment),
            deeplink
        )
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

    override fun goToMainScreen(bundle: Bundle?) {
        navigator.goToScreen(ActivityEvent(SmartHomeActivity::class.java, bundle))
        navigator.close()
    }

    override fun goToLoginScreen() {
        navigator.goToScreen(ActivityEvent(LoginActivity::class.java))
        navigator.close()
    }

    fun goToRegisterScreen() {
        navigator.goToScreen(ActivityEvent(RegisterActivity::class.java))
    }

    override fun goToScreenBasedOnDeeplinkIntent(intent: Intent) {
        when (intent.getStringExtra(DESTINATION_URL)) {
            DASHBOARD_DESTINATION_URL -> goToDashboard(true)
            HOME_DESTINATION_URL -> {
                intent.putExtra(POSITION_HOME_ON_VIEW_PAGER_KEY, POSITION_HOME_ON_VIEW_PAGER)
                goToHome(intent.extras,true)
            }
            BLINDS_DESTINATION_URL -> goToBlindDetailsScreen(
                intent.extras,
                true
            )
            LIGHT_DESTINATION_URL -> goToLightsDetailsScreen(
                intent.extras,
                true
            )
            HVAC_DESTINATION_URL -> goToHvacDetalisScreen(
                intent.extras,
                true
            )
            TEMPERATURE_DESTINATION_URL -> goToTemperatureDetailsScreen(
                intent.extras,
                true
            )
            else -> goToSmartHome(false)
        }
        intent.removeExtra(DESTINATION_URL)
    }
}