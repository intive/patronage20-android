package com.intive.patronage.smarthome.feature.login.animation

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.navigator.DashboardCoordinator

fun startRegisterActivityEnterAnimation(
    context: Context,
    registerCard: View,
    registerInfo: View
) {
    val fromRight = AnimationUtils.loadAnimation(context, R.anim.from_right)
    val fromBottom = AnimationUtils.loadAnimation(context, R.anim.from_bottom)

    registerCard.startAnimation(fromRight)
    registerInfo.startAnimation(fromBottom)
}

fun startRegisterActivityExitAnimation(
    context: Context,
    coordinator: DashboardCoordinator,
    registerCard: View,
    registerInfo: View
) {
    val toBottom = AnimationUtils.loadAnimation(context, R.anim.to_bottom)
    val toRight = AnimationUtils.loadAnimation(context, R.anim.to_right)

    toRight.setAnimationListener(object: AnimationListenerWrapper() {
        override fun onAnimationEnd(animation: Animation?) {
            super.onAnimationEnd(animation)
            coordinator.goToLoginScreen()
        }
    })

    registerInfo.startAnimation(toBottom)
    registerCard.startAnimation(toRight)
}