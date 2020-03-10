package com.intive.patronage.smarthome.spashscreen

import android.app.Activity
import android.app.AlertDialog

class CustomAlertDialog {

    fun connectionError(activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle("Error")
            .setMessage("Unable to connect with smart home")
            .setNegativeButton("Quit") { _, _ -> activity.finish() }
            .setCancelable(false)
            .show()
    }
}