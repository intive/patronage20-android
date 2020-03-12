package com.intive.patronage.smarthome.navigator

import androidx.appcompat.app.AppCompatActivity

class Navigator(private val activity: AppCompatActivity) {

    fun goToScreen(event: NavigationEvent) {
        when(event) {
            /*Old solution
            val fragment = event.buildFragment()
            activity.supportFragmentManager
                    .beginTransaction()
                    .replace(event.containerId,fragment)
                    .addToBackStack(null)
                    .commit()
             */
            is FragmentEvent -> {
                val fragment = event.buildFragment()
                val fragments = activity.supportFragmentManager.fragments

                if (activity.supportFragmentManager.backStackEntryCount > 0) {
                    val f = fragments[activity.supportFragmentManager.backStackEntryCount - 1]

                    if(f::class.java !== fragment::class.java){
                        activity.supportFragmentManager.beginTransaction().add(event.containerId, fragment).addToBackStack(null).commit()
                    }
                } else {
                    activity.supportFragmentManager.beginTransaction().add(event.containerId, fragment).addToBackStack(null).commit()
                }
            }
            is ActivityEvent -> {
                val intent = event.createIntent(activity)
                activity.startActivity(intent)
            }
        }
    }

    fun goBack() {
        activity.supportFragmentManager.beginTransaction().also {
            if (it.isEmpty) {
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