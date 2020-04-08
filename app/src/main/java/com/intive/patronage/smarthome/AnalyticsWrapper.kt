package com.intive.patronage.smarthome

import android.app.Activity
import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsWrapper(appContext : Context){

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    init {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(appContext)
    }

    fun switchScreenEvent(activity: Activity?, name: String){
        mFirebaseAnalytics?.setCurrentScreen(activity!!, name, name)
    }
}