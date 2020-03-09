package com.intive.patronage.smarthome.navigator

import android.os.Bundle
import com.intive.patronage.smarthome.*

class DashBoardCoordinator(private val navigator: Navigator, private val bundle: Bundle?) {

    fun goToLedDetailScreen() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(LedFragment::class.java, bundle))
    }

    fun goToTemperatureDetailScreen() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(TemperatureFragment::class.java, bundle))
    }

    fun goToWindowDetailScreen() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(WindowFragment::class.java, bundle))
    }

    fun goToWindowBlindsDetailScreen() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(WindowBlindsFragment::class.java, bundle))
    }

    fun goToRFIDDetailScreen() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(RFIDFragment::class.java, bundle))
    }

    fun goToSmokeDetailScreen() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(SmokeFragment::class.java, bundle))
    }

    fun goToHVACDetailScreen() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(HVACFragment::class.java, bundle))
    }

    fun goToHVACRoomsDetailScreen() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(HVACRoomsFragment::class.java, bundle))
    }

    fun goToBackDashBoard() {
        //uncomment and replace Fragment::class
        //navigator.goToScreen(FragmentEvent(DashBoardFragment::class.java, bundle))
    }

    fun goBack() {
        navigator.goBack()
    }

    fun close() {
        navigator.close()
    }
}