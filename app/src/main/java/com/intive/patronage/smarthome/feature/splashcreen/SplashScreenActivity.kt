package com.intive.patronage.smarthome.feature.splashcreen

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.navigator.SplashScreenCoordinator
import com.intive.patronage.smarthome.common.SmartHomeAlertDialog
import com.intive.patronage.smarthome.feature.splashcreen.viewmodel.SplashScreenViewModel
import io.reactivex.Completable
import io.reactivex.Single
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SplashScreenActivity : AppCompatActivity() {

    private val alertDialog: SmartHomeAlertDialog by inject()
    private val splashScreenViewModel: SplashScreenViewModel by viewModel()
    private val splashScreenCoordinator: SplashScreenCoordinator by inject { parametersOf(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        observeViewModel()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) fullScreen()
    }

    private fun observeViewModel() {
        splashScreenViewModel.error.observe(this, Observer { error ->
            if (error) alertDialog.showSmartHomeDialog(
                this, R.string.error_title,
                R.string.connection_error_message
            ) { finish() }
        })

        val data = intent?.data
        splashScreenViewModel.complete.observe(this, Observer { complete ->
            if (complete) {
                data?.let {
                    goToScreenBasedOnDeeplink(data)
                } ?: run {
                    splashScreenCoordinator.goToMainScreen()
                }
            }
        })
    }

    private fun goToScreenBasedOnDeeplink(data: Uri?) {
        when (data?.getQueryParameter(getString(R.string.deeplink_destination))) {
            getString(R.string.deeplink_destination_home) ->
                splashScreenCoordinator.goToMainScreen(
                    createBundleWithString(
                        getString(R.string.deeplink_destination),
                        getString(R.string.deeplink_destination_home)
                    )
                )
            getString(R.string.deeplink_destination_light) ->
                splashScreenCoordinator.goToMainScreen(
                    createBundleWithStringAndId(
                        getString(R.string.deeplink_destination),
                        getString(R.string.deeplink_destination_light),
                        data
                    )
                )
            getString(R.string.deeplink_destination_blinds) ->
                splashScreenCoordinator.goToMainScreen(
                    createBundleWithStringAndId(
                        getString(R.string.deeplink_destination),
                        getString(R.string.deeplink_destination_blinds),
                        data
                    )
                )
            getString(R.string.deeplink_destination_hvac) ->
                splashScreenCoordinator.goToMainScreen(
                    createBundleWithStringAndId(
                        getString(R.string.deeplink_destination),
                        getString(R.string.deeplink_destination_hvac),
                        data
                    )
                )
            getString(R.string.deeplink_destination_temperature) ->
                splashScreenCoordinator.goToMainScreen(
                    createBundleWithStringAndId(
                        getString(R.string.deeplink_destination),
                        getString(R.string.deeplink_destination_temperature),
                        data
                    )
                )
            getString(R.string.deeplink_destination_developer) -> splashScreenCoordinator.goToDeveloperSettings()
            else -> splashScreenCoordinator.goToMainScreen()
        }
    }

    private fun createBundleWithString(tag: String, value: String): Bundle {
        val bundle = Bundle()
        bundle.putString(tag, value)
        return bundle
    }

    private fun createBundleWithStringAndId(tag: String, value: String, data: Uri?): Bundle {
        val bundle = Bundle()
        val id = data?.getQueryParameter("id")
        id?.let { Integer.parseInt(it) }?.let { bundle.putInt("ID", it) }
        bundle.putString(tag, value)
        return bundle
    }

    // Setting fullscreen
    private fun fullScreen() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)

    }

}
