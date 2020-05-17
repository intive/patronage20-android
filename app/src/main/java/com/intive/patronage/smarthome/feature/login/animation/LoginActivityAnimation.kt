package com.intive.patronage.smarthome.feature.login.animation

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.navigator.DashboardCoordinator

fun startLoginActivityEnterAnimation(
    context: Context,
    loginCard: View,
    loginInfo: View
) {
    val fromLeft = AnimationUtils.loadAnimation(context, R.anim.from_left)
    val fromBottom = AnimationUtils.loadAnimation(context, R.anim.from_bottom)

    loginCard.startAnimation(fromLeft)
    loginInfo.startAnimation(fromBottom)
}

fun startLoginActivityExitAnimation(
    context: Context,
    coordinator: DashboardCoordinator,
    loginCard: View,
    loginInfo: View
) {
    val toBottom = AnimationUtils.loadAnimation(context, R.anim.to_bottom)
    val toLeft = AnimationUtils.loadAnimation(context, R.anim.to_left)

    toLeft.setAnimationListener(object: AnimationListenerWrapper() {
        override fun onAnimationEnd(animation: Animation?) {
            super.onAnimationEnd(animation)
            coordinator.goToRegisterScreen()
        }
    })

    loginInfo.startAnimation(toBottom)
    loginCard.startAnimation(toLeft)
}