package com.intive.patronage.smarthome.splashscreen

import android.app.Activity
import android.app.AlertDialog
import com.intive.patronage.smarthome.R

class SmartHomeAlertDialog(private val activity: Activity) {

    fun connectionError() {
        AlertDialog.Builder(activity)
            .setTitle(R.string.error_title)
            .setMessage(R.string.connection_error_message)
            .setNegativeButton((R.string.quit)) { _, _ -> activity.finish() }
            .setCancelable(false)
            .show()
    }
}