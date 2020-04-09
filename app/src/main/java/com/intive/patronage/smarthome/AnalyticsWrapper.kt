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

    fun hvacTempEvent(coolingTemp: Int, heatingTemp: Int){
        val params = Bundle()
        params.putFloat("cooling_temperature", coolingTemp/10f)
        params.putFloat("heating_temperature", heatingTemp/10f)
        mFirebaseAnalytics?.logEvent("hvac_status", params)
    }
}