package com.intive.patronage.smarthome.navigator

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.intive.patronage.smarthome.AnalyticsWrapper
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeFragment
import org.koin.core.KoinComponent

class Navigator(
    private val activity: AppCompatActivity,
    private val analytics: AnalyticsWrapper
): KoinComponent {

    fun goToScreen(event: NavigationEvent, deeplink: Boolean = false) {

        when (event) {
            is FragmentEvent -> {
                val fragment = event.buildFragment()

                activity.supportFragmentManager.also {
                    analytics.switchScreenEvent(activity, fragment.javaClass.simpleName)
                    val topFragment = it.findFragmentByTag("${fragment.javaClass}")
                    if (topFragment != null) it.popBackStack()

                    if (deeplink)
                        adjustToDeeplinkLogic(it, event, fragment)

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

    private fun adjustToDeeplinkLogic(fragmentManager: FragmentManager, event: FragmentEvent, fragment: Fragment) {
        if (fragmentManager.fragments.size > 1)
            goBack()
        else if (fragmentManager.fragments.size == 0 && fragment !is SmartHomeFragment) {
            fragmentManager.beginTransaction()
                .add(
                    event.containerId,
                    SmartHomeFragment(),
                    "${SmartHomeFragment::class.java}"
                )
                .addToBackStack(null)
                .commit()
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

    fun close(affinity: Boolean = false) {
        if (!affinity) {
            activity.finish()
        } else {
            activity.finishAffinity()
        }
    }
}
