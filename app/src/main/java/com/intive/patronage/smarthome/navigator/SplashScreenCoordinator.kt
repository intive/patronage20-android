package com.intive.patronage.smarthome.navigator

import android.net.Uri
import android.os.Bundle
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.developer.view.DeveloperSettingsActivity


class SplashScreenCoordinator(private val navigator: Navigator) {

    fun goToMainScreen(bundle: Bundle? = null) {
        navigator.goToScreen(ActivityEvent(SmartHomeActivity::class.java, bundle))
        navigator.close()
    }

    fun goToDeveloperSettings() {
        navigator.goToScreen(ActivityEvent(DeveloperSettingsActivity::class.java))
        navigator.close()
    }

    fun goToScreenBasedOnDeeplinkUri(data: Uri?) {
        val queryParameter = data?.getQueryParameter(DESTINATION_URL)

        queryParameter?.let {
            goToMainScreen(
                createBundleWithStringAndId(
                    DESTINATION_URL,
                    it,
                    data
                )
            )
        } ?: goToMainScreen()

    }

    private fun createBundleWithStringAndId(tag: String, value: String, data: Uri?): Bundle {
        val bundle = Bundle()
        val id = data?.getQueryParameter("id")
        id?.let { bundle.putInt("ID", Integer.parseInt(it)) }
        bundle.putString(tag, value)
        return bundle
    }
}