package com.intive.patronage.smarthome.navigator

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.intive.patronage.smarthome.AnalyticsWrapper
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.view.DashboardFragment
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeFragment
import kotlinx.android.synthetic.main.smart_home_activity.view.*
import org.koin.core.KoinComponent

class Navigator(private val activity: AppCompatActivity, private val analytics: AnalyticsWrapper) :
    KoinComponent {

    fun goToScreen(event: NavigationEvent, deeplink: Boolean = false) {

        when (event) {
            is FragmentEvent -> {
                val fragment = event.buildFragment()

                activity.supportFragmentManager.also {
                    analytics.switchScreenEvent(activity, fragment.javaClass.simpleName)
                    val topFragment = it.findFragmentByTag("${fragment.javaClass}")
                    if (topFragment != null) it.popBackStack()

                    if (deeplink) {
                        if (it.fragments.size > 1)
                            goBack()
                        else if (it.fragments.size == 0) {
                            it.beginTransaction()
                                .add(
                                    event.containerId,
                                    SmartHomeFragment(),
                                    "${SmartHomeFragment::class.java}"
                                )
                                .addToBackStack(null)
                                .commit()
                        }
                    }

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
