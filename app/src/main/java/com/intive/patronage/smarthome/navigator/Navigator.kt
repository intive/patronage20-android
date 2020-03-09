package com.intive.patronage.smarthome.navigator

import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R

class Navigator(private val activity: AppCompatActivity) {

    fun goToScreen(event: NavigationEvent) {
        when(event) {
            is FragmentEvent -> {
                val fragment = event.buildFragment()
                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main,fragment)
                    .addToBackStack(null)
                    .commit()
            }
            is ActivityEvent -> {
                val intent = event.createIntent(activity)
                activity.startActivity(intent)
                close()
            }
        }
    }

    fun goBack() {
        activity.supportFragmentManager.beginTransaction().also {
            if (it.isEmpty) {
                activity.onBackPressed()
            } else {
                activity.supportFragmentManager.popBackStack()
            }
        }
    }

    fun close() {
        activity.finish()
    }
}