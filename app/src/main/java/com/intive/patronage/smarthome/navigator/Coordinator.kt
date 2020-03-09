package com.intive.patronage.smarthome.navigator

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment

class Coordinator(private val navigator: Navigator) {

    fun goToScreen(clazz: Class<*>, bundle: Bundle?) {
        if (Fragment::class.java.isAssignableFrom(clazz)) {
            navigator.goToScreen(FragmentEvent(clazz, bundle))
        }
        if (Activity::class.java.isAssignableFrom(clazz)) {
            navigator.goToScreen(ActivityEvent(clazz, bundle))
        }
    }

    fun goBack() {
        navigator.goBack()
    }

    fun close() {
        navigator.close()
    }
}