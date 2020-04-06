package com.intive.patronage.smarthome.common

import android.app.Activity
import android.graphics.Color
import com.google.android.material.snackbar.Snackbar


class SmartHomeErrorSnackbar(activity: Activity) {

    val snackBar = Snackbar.make(activity.findViewById(android.R.id.content),
        "", Snackbar.LENGTH_SHORT)

    fun showSnackbar(message: String) {
//        val params = snackBar.view.layoutParams as FrameLayout.LayoutParams
//        params.gravity = Gravity.TOP
//        params.topMargin = 132
//        snackBar.view.layoutParams = params

        snackBar.setText(message)
            .setBackgroundTint(Color.parseColor("#B00020"))
            .setTextColor(Color.WHITE)
        snackBar.show()
    }

}