package com.intive.patronage.smarthome.navigator

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.AnalyticsWrapper
import kotlinx.android.synthetic.main.smart_home_activity.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.inject

class Navigator(private val activity: AppCompatActivity): KoinComponent {

    private val mFirebaseAnalytics: AnalyticsWrapper by inject()

    fun goToScreen(event: NavigationEvent) {

        when (event) {
            is FragmentEvent -> {
                val fragment = event.buildFragment()

                activity.supportFragmentManager.also {
                    mFirebaseAnalytics.switchScreenEvent(activity, fragment.javaClass.simpleName)
                    val topFragment = it.findFragmentByTag("${fragment.javaClass}")
                    if (topFragment != null) it.popBackStack()

                    it.beginTransaction()
                        .replace(event.containerId, fragment, "${fragment.javaClass}")
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
