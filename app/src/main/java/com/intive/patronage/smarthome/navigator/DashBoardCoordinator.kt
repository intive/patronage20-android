package com.intive.patronage.smarthome.navigator

import android.os.Bundle
import com.intive.patronage.smarthome.*

class DashBoardCoordinator(private val navigator: Navigator) {

    fun goToLedDetailScreen(bundle: Bundle? = null) {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(LedFragment::class.java, bundle))
    }

    fun goToDashBoard() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(DashBoardFragment::class.java, null))
    }

    fun goBack() {
        navigator.goBack()
    }
}