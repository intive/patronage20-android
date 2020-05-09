package com.intive.patronage.smarthome.navigator

import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.AnalyticsWrapper
import org.koin.core.KoinComponent

class Navigator(private val activity: AppCompatActivity, private val analytics: AnalyticsWrapper): KoinComponent {

    fun goToScreen(event: NavigationEvent) {

        when (event) {
            is FragmentEvent -> {
                val fragment = event.buildFragment()

                activity.supportFragmentManager.also {
                    analytics.switchScreenEvent(activity, fragment.javaClass.simpleName)
                    val topFragment = it.findFragmentByTag("${fragment.javaClass}")
                    if (topFragment != null) it.popBackStack()

                    it.beginTransaction()
                        .add(event.containerId, fragment, "${fragment.javaClass}")
                        .addToBackStack(null)
                        .commit()
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
            if ((it.backStackEntryCount == 1) or (it.backStackEntryCount == 0)) {
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
