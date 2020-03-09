package com.intive.patronage.smarthome.navigator

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class ActivityEvent(clazz: Class<*>, bundle: Bundle? = null) : NavigationEvent(clazz, bundle){

    fun createIntent(activity: Activity) : Intent {
        return Intent(activity, clazz).apply {
            bundle?.let {
                putExtras(it)
            }
        }
    }
}