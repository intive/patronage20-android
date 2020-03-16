package com.intive.patronage.smarthome.splashscreen

import android.app.Activity
import android.app.AlertDialog
import com.intive.patronage.smarthome.R


class SmartHomeAlertDialog {


    fun showSmartHomeDialog(activity: Activity, titleId: Int, messageId: Int, onClose: () -> Unit) {
        AlertDialog.Builder(activity)
            .setTitle(titleId)
            .setMessage(messageId)
            .setNegativeButton(R.string.quit) { _, _ -> onClose() }
            .setCancelable(false)
            .show()
    }
}