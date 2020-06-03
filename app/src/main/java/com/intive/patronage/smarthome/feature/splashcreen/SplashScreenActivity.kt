package com.intive.patronage.smarthome.feature.splashcreen

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.DeeplinkService
import com.intive.patronage.smarthome.common.SmartHomeAlertDialog
import com.intive.patronage.smarthome.feature.splashcreen.animation.ProgressBarAnimation
import com.intive.patronage.smarthome.feature.splashcreen.viewmodel.SplashScreenViewModel
import com.intive.patronage.smarthome.navigator.SplashScreenCoordinator
import com.intive.patronage.smarthome.notifications.service.SmartHomeNotificationsService
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SplashScreenActivity : AppCompatActivity() {

    private val alertDialog: SmartHomeAlertDialog by inject()
    private val splashScreenViewModel: SplashScreenViewModel by viewModel()
    private val splashScreenCoordinator: SplashScreenCoordinator by inject {
        parametersOf(this)
    }
    private val deeplinkService: DeeplinkService by inject {
        parametersOf(splashScreenCoordinator)
    }

    private val progressBarAnimation: ProgressBarAnimation by inject()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashScreenViewModel.error.observe(this, Observer { error ->
            if (error) alertDialog.showSmartHomeDialog(
                this, R.string.error_title,
                R.string.connection_error_message
            ) { finish() }
        })

        initProgressBar()
        startProgressAnimation()
    }

    override fun onResume() {
        super.onResume()
        startSlideInAnimation()
    }

    private fun startSlideInAnimation() {
        splashScreenIcon.foreground = ContextCompat.getDrawable(this, R.drawable.wifi_animation)
        val wifiAnimation = splashScreenIcon.foreground as AnimationDrawable
        wifiAnimation.start()

        val fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_splash_screen)
        val fromTop = AnimationUtils.loadAnimation(this, R.anim.from_top_splash_screen)

        iconLayout.startAnimation(fromBottom)
        splashScreenLogo.startAnimation(fromTop)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) fullScreen()
    }

    private fun initProgressBar() {
        progressBar = findViewById(R.id.splashScreenProgressBar)
    }

    private fun startProgressAnimation() {
        progressBarAnimation.startInitialPhase(progressBar, ::startProgressAnimationSecondPhase)
    }

    private fun startProgressAnimationSecondPhase() {
        splashScreenViewModel.complete.observe(this,  Observer { complete ->
            if (!complete) {
                progressBarAnimation.startLoadingPhase(progressBar)
            } else {
                progressBarAnimation.cancel()
                progressBarAnimation.startFinishingPhase(progressBar, ::onLoadingFinished)
            }
        })
    }

    private fun onLoadingFinished() {
        deeplinkService.handleDeeplinkRedirectionOnLoadingEnd(intent)
        startNotificationsService()
    }

    private fun startNotificationsService() {
        Intent(this, SmartHomeNotificationsService::class.java).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(it)
            } else {
                startService(it)
            }
        }
    }

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