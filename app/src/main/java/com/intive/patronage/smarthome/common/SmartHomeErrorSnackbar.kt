package com.intive.patronage.smarthome.common

import android.app.Activity
import android.graphics.Color
import com.google.android.material.snackbar.Snackbar
import com.intive.patronage.smarthome.R


class SmartHomeErrorSnackbar(val activity: Activity) {

    val snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
        "", Snackbar.LENGTH_INDEFINITE)

    fun showSnackbar(message: String) = snackbar.setText(message)
            .setBackgroundTint(activity.getColor(R.color.error))
            .setTextColor(Color.WHITE)
            .show()


    fun hideSnackbar() {
        if (snackbar.isShown) snackbar.dismiss()
    }

}