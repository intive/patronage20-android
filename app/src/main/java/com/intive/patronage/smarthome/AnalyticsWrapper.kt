package com.intive.patronage.smarthome

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsWrapper(appContext : Context){

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    init {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(appContext)
    }

    fun switchScreenEvent(activity: Activity?, name: String){
        mFirebaseAnalytics?.setCurrentScreen(activity!!, name, name)
    }

    fun ledColorEvent(color: Int){
        val params = Bundle()
        params.putString("color", String.format("#%06X", 0xFFFFFF and color))
        mFirebaseAnalytics?.logEvent("led_color", params)
    }

    fun blindLevelEvent(position: Int){
        val params = Bundle()
        params.putInt("blind_position", position)
        mFirebaseAnalytics?.logEvent("blind_position", params)
    }

    fun hvacEvent(details: List<String>){
        val params = Bundle()
        params.putFloat("heating_temperature", details[0].toInt()/10f)
        params.putFloat("cooling_temperature", details[1].toInt()/10f)
        params.putInt("hysteresis", details[2].toInt())
        mFirebaseAnalytics?.logEvent("hvac_status", params)
    }
}