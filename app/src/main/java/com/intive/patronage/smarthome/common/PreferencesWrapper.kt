package com.intive.patronage.smarthome.common

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val DARK_MODE_KEY = "DARK_MODE"

class PreferencesWrapper(appContext: Context) {

    var sharedPref: SharedPreferences

    init {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext)
    }

    fun checkIfContains(key: String) = sharedPref.contains(key)

    fun getBooleanFromPreference(key: String) = sharedPref.getBoolean(key, true)

    fun setDarkModePreference(bool: Boolean){
        with (sharedPref.edit()){
            putBoolean(DARK_MODE_KEY, bool)
            commit()
        }
    }
}