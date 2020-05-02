package com.intive.patronage.smarthome.feature.splashcreen

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.SmartHomeAlertDialog
import com.intive.patronage.smarthome.feature.splashcreen.viewmodel.SplashScreenViewModel
import com.intive.patronage.smarthome.navigator.SplashScreenCoordinator
import com.intive.patronage.smarthome.notifications.service.NotificationsService
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SplashScreenActivity : AppCompatActivity() {

    private val alertDialog: SmartHomeAlertDialog by inject()
    private val splashScreenViewModel: SplashScreenViewModel by viewModel()
    private val splashScreenCoordinator: SplashScreenCoordinator by inject { parametersOf(this) }

    private val slideDuration = 1000L
    private val fadeDuration = 2000L
    private val progressOnCompleteDuration = 1000L

    lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashScreenViewModel.error.observe(this, Observer { error ->
            if (error) alertDialog.showSmartHomeDialog(
                this, R.string.error_title,
                R.string.connection_error_message
            ) { finish() }
        })

        startSlideInAnimation()
        startProgressBarAnimation()
    }

    private fun startSlideInAnimation() {
        val icon = findViewById<ImageView>(R.id.splashScreenIcon)
        val iconLayout = findViewById<RelativeLayout>(R.id.iconLayout)
        val logo = findViewById<ImageView>(R.id.splashScreenLogo)

        icon.foreground = resources.getDrawable(R.drawable.wifi_animation)
        val wifiAnimation = icon.foreground as AnimationDrawable
        wifiAnimation.start()

        val fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom)
        fromBottom.duration = slideDuration

        val fromTop = AnimationUtils.loadAnimation(this, R.anim.from_top)
        fromTop.duration = slideDuration

        icon.animation = fromBottom
        iconLayout.animation = fromBottom
        logo.animation = fromTop

        icon.animate().alpha(1f).duration = fadeDuration
        iconLayout.animate().alpha(1f).duration = fadeDuration
        logo.animate().alpha(1f).duration = fadeDuration
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) fullScreen()
    }

    private fun startProgressBarAnimation() {
        val progressBar = findViewById<ProgressBar>(R.id.splashScreenProgressBar)
        val progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, progressBar.max / 2)
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.duration = splashScreenViewModel.minWaitTime * 1000

        progressAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationEnd(animation: Animator) {
                var timeUntilMaxWaitTime = (splashScreenViewModel.maxWaitTime - splashScreenViewModel.minWaitTime) * 1000

                timer = object : CountDownTimer(timeUntilMaxWaitTime, 1) {
                    override fun onTick(millisUntilFinished: Long) {
                        timeUntilMaxWaitTime--
                    }

                    override fun onFinish() {}
                }.start()

                val animator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, progressBar.max)

                splashScreenViewModel.complete.observe(this@SplashScreenActivity, Observer { complete ->
                    if (!complete) {
                        animator.interpolator = LinearInterpolator()
                        animator.duration = timeUntilMaxWaitTime
                        animator.start()
                    } else {
                        animator.cancel()

                        val animatorOnComplete = ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, progressBar.max)
                        animatorOnComplete.interpolator = LinearInterpolator()
                        animatorOnComplete.duration = progressOnCompleteDuration

                        animatorOnComplete.addListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator) {}
                            override fun onAnimationRepeat(p0: Animator?) {}
                            override fun onAnimationCancel(p0: Animator?) {}

                            override fun onAnimationEnd(animation: Animator) {
                                onLoadingEnd(complete)
                            }
                        })
                        animatorOnComplete.start()
                    }
                })
            }
        })
        progressAnimator.start()
    }

    private fun onLoadingEnd(complete: Boolean) {
        val data = intent?.data

        if (complete && FirebaseAuth.getInstance().currentUser != null) {
            data?.let {
                splashScreenCoordinator.goToScreenBasedOnDeeplinkUri(data)
            } ?: splashScreenCoordinator.goToMainScreen()
        } else if (complete && FirebaseAuth.getInstance().currentUser == null) {
            splashScreenCoordinator.goToLoginScreen()
        }

        Intent(this, NotificationsService::class.java).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //startForegroundService(it)
                startService(it)
            } else {
                startService(it)
            }
        }

        timer.cancel()

//      if (complete)
//          data?.let { splashScreenCoordinator.goToScreenBasedOnDeeplinkUri(data) }
//              ?: splashScreenCoordinator.goToMainScreen()
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