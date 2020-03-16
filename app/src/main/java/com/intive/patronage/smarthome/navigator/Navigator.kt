package com.intive.patronage.smarthome.navigator

import androidx.appcompat.app.AppCompatActivity

class Navigator(private val activity: AppCompatActivity) {

    fun goToScreen(event: NavigationEvent) {
        when (event) {
            is FragmentEvent -> {
                val fragment = event.buildFragment()

                activity.supportFragmentManager.also {
                    val topFragment = it.findFragmentByTag("${fragment.javaClass}")

                    if (topFragment == null) {
                        it.beginTransaction()
                            .add(event.containerId, fragment, "${fragment.javaClass}")
                            .addToBackStack(null)
                            .commit()
                    } else {
                        it.popBackStack()
                        it.beginTransaction()
                            .add(event.containerId, fragment, "${fragment.javaClass}")
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
            is ActivityEvent -> {
                val intent = event.createIntent(activity)
                activity.startActivity(intent)
            }
        }
    }

    fun goBack() {
        activity.supportFragmentManager.also {
            if (it.backStackEntryCount == 1) {
                activity.finish()
            } else {
                activity.supportFragmentManager.popBackStack()
            }
        }
    }

    fun close() {
        activity.finish()
    }
}