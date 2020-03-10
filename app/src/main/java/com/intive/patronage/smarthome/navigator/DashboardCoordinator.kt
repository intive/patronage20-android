package com.intive.patronage.smarthome.navigator

import android.os.Bundle

class DashboardCoordinator(private val navigator: Navigator) {

    fun goToLightsDetailsScreen(bundle: Bundle? = null) {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(LedFragment::class.java, bundle))
    }

    fun goToDashboard() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(DashBoardFragment::class.java, null))
    }

    fun goBack() {
        navigator.goBack()
    }
}