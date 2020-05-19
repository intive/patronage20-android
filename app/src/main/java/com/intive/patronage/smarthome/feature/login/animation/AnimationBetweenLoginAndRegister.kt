package com.intive.patronage.smarthome.feature.login.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.navigator.DashboardCoordinator

fun startEnterAnimation(
    card: View,
    info: View,
    isOnTheLeft: Boolean
) {
    val fromBottom = AnimationUtils.loadAnimation(card.context, R.anim.from_bottom)
    val cardAnimation = if (isOnTheLeft) {
        AnimationUtils.loadAnimation(card.context, R.anim.from_left)
    } else {
        AnimationUtils.loadAnimation(card.context, R.anim.from_right)
    }

    card.startAnimation(cardAnimation)
    info.startAnimation(fromBottom)
}

fun startExitAnimation(
    coordinator: DashboardCoordinator,
    card: View,
    info: View,
    isOnTheLeft: Boolean
) {
    val toBottom = AnimationUtils.loadAnimation(card.context, R.anim.to_bottom)
    val cardAnimation = if (isOnTheLeft) {
        AnimationUtils.loadAnimation(card.context, R.anim.to_left)
    } else {
        AnimationUtils.loadAnimation(card.context, R.anim.to_right)
    }

    cardAnimation.setAnimationListener(object: AnimationListenerWrapper() {
        override fun onAnimationEnd(animation: Animation?) {
            super.onAnimationEnd(animation)
            if (isOnTheLeft) {
                coordinator.goToRegisterScreen()
            } else {
                coordinator.goToLoginScreen()
            }
        }
    })

    card.startAnimation(cardAnimation)
    info.startAnimation(toBottom)
}