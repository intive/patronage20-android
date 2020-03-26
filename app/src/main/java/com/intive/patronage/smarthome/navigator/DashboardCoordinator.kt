package com.intive.patronage.smarthome.navigator

import android.os.Bundle
import com.intive.patronage.smarthome.R

import com.intive.patronage.smarthome.feature.blind.view.BlindDetailsFragment
import com.intive.patronage.smarthome.feature.dashboard.view.DashboardFragment
import com.intive.patronage.smarthome.feature.developer.view.DeveloperSettingsActivity
import com.intive.patronage.smarthome.feature.light.view.LightsDetailsFragment

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

    fun goToBlindDetailsScreen(bundle: Bundle? = null) {
        navigator.goToScreen(
            FragmentEvent(
                BlindDetailsFragment::class.java,
                bundle,
                R.id.fragment
            )
        )
    }

    fun goToDashboard() {
        //uncomment and replace Fragment::class
        navigator.goToScreen(FragmentEvent(DashboardFragment::class.java, null, R.id.fragment))
    }

    fun goBack() {
        navigator.goBack()
    }

    fun goToDeveloperSettings() {
        navigator.goToScreen(ActivityEvent(DeveloperSettingsActivity::class.java))
    }
}