package com.intive.patronage.smarthome.feature.splashcreen.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import com.intive.patronage.smarthome.feature.splashcreen.viewmodel.MAX_WAIT_TIME
import com.intive.patronage.smarthome.feature.splashcreen.viewmodel.MIN_WAIT_TIME

private const val PROGRESS_BAR_NAME = "progress"
private const val LAST_PHASE_DURATION = 1000L
private const val MILLIS_TO_SECONDS_MULTIPLIER = 1000

class ProgressBarAnimation {

    private lateinit var progressAnimator: ObjectAnimator

    fun startInitialPhase(progressBar: ProgressBar, onEnd: () -> Unit) =
        animate(progressBar, onEnd, MIN_WAIT_TIME * MILLIS_TO_SECONDS_MULTIPLIER, progressBar.max / 2)

    fun startLoadingPhase(progressBar: ProgressBar) =
        animate(progressBar, {}, (MAX_WAIT_TIME - MIN_WAIT_TIME) * MILLIS_TO_SECONDS_MULTIPLIER, progressBar.max)

    fun startFinishingPhase(progressBar: ProgressBar, onEnd: () -> Unit) =
        animate(progressBar, onEnd, LAST_PHASE_DURATION, progressBar.max)

    fun cancel() {
        if (::progressAnimator.isInitialized) {
            progressAnimator.cancel()
        }
    }

    private fun animate(progressBar: ProgressBar, onEnd: () -> Unit , duration: Long, limit: Int) {
        progressAnimator = ObjectAnimator.ofInt(progressBar, PROGRESS_BAR_NAME, progressBar.progress, limit)
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.duration = duration
        progressAnimator.addListener(object: AnimatorListenerWrapper() {
            override fun onAnimationEnd(animation: Animator?) {
                onEnd()
            }
        })
        progressAnimator.start()
    }
}