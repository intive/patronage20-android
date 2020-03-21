package com.intive.patronage.smarthome.navigator

import android.os.Bundle
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.view.DashboardFragment
import com.intive.patronage.smarthome.feature.dashboard.view.HomeFragment
import com.intive.patronage.smarthome.feature.dashboard.view.LightsDetailsFragment

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

    fun goToDashboard() {
        //uncomment and replace Fragment::class
        navigator.goToScreen(FragmentEvent(DashboardFragment::class.java, null, R.id.fragment))
    }

    fun goToHome(){
        navigator.goToScreen(FragmentEvent(HomeFragment::class.java, null, R.id.fragment))
    }

    fun goBack() {
        navigator.goBack()
    }
}