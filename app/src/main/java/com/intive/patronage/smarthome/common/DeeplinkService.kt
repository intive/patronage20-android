package com.intive.patronage.smarthome.common

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.intive.patronage.smarthome.navigator.DeeplinkCoordinator

const val DESTINATION_URL = "destination"
const val DASHBOARD_DESTINATION_URL = "dashboard"
const val HOME_DESTINATION_URL = "home"
const val BLINDS_DESTINATION_URL = "blinds"
const val LIGHT_DESTINATION_URL = "light"
const val HVAC_DESTINATION_URL = "hvac"
const val TEMPERATURE_DESTINATION_URL = "temperature"
const val ID_QUERY_URL = "id"
const val ID_PARAMETER_URL = "ID"

class DeeplinkService(private val deeplinkCoordinator: DeeplinkCoordinator) {

    fun handleDeeplinkRedirectionOnLoadingEnd(intent: Intent) {
        val data = intent.data
        if (FirebaseAuth.getInstance().currentUser != null) {
            data?.let {
                handleDeeplinkRedirectionInDashboard(intent)
            } ?: deeplinkCoordinator.goToMainScreen()
        } else if (FirebaseAuth.getInstance().currentUser == null) {
            deeplinkCoordinator.goToLoginScreen()
        }
    }

    fun handleDeeplinkRedirectionInDashboard(intent: Intent, savedInstanceState: Bundle? = null) {
        if (savedInstanceState == null || (intent.extras != null && intent.extras?.containsKey(
                DESTINATION_URL
            )!!)
        ) {
            deeplinkCoordinator.goToScreenBasedOnDeeplinkIntent(intent)
        }
    }
}