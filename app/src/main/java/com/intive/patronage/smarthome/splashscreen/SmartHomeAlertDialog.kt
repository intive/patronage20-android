package com.intive.patronage.smarthome.splashscreen

import android.app.Activity
import android.app.AlertDialog

class SmartHomeAlertDialog(private val activity: Activity) {

    fun connectionError() {
        AlertDialog.Builder(activity)
            .setTitle("Error")
            .setMessage("Unable to connect with smart home")
            .setNegativeButton("Quit") { _, _ -> activity.finish() }
            .setCancelable(false)
            .show()
    }
}