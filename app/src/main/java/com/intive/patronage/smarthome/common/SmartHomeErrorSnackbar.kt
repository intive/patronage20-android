package com.intive.patronage.smarthome.common

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import com.intive.patronage.smarthome.R


class SmartHomeErrorSnackbar(val activity: Activity) {

    val snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
        "", Snackbar.LENGTH_INDEFINITE)

    fun showSnackbar(message: String) =
        snackbar.setText(message)
            .setBackgroundTint(activity.getColor(R.color.error))
                //Color.parseColor("#B00020")
            .setTextColor(Color.WHITE)
            .show()


    fun hideSnackbar() = snackbar.dismiss()

}