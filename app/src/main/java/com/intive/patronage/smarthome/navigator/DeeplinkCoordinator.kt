package com.intive.patronage.smarthome.navigator

import android.content.Intent
import android.os.Bundle

interface DeeplinkCoordinator {
    fun goToScreenBasedOnDeeplinkIntent(intent: Intent)
    fun goToMainScreen(bundle: Bundle? = null)
    fun goToLoginScreen()
}