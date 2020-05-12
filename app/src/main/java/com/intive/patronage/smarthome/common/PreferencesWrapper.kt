package com.intive.patronage.smarthome.common

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val DARK_MODE_KEY = "DARK_MODE"
const val NOTIFICATIONS_VISIBILITY = "NOTIFICATIONS_VISIBILITY"

class PreferencesWrapper(appContext: Context) {

    var sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun checkIfContains(key: String) = sharedPref.contains(key)

    fun getBooleanFromPreference(key: String) = sharedPref.getBoolean(key, true)

    fun setDarkModePreference(bool: Boolean){
        with (sharedPref.edit()){
            putBoolean(DARK_MODE_KEY, bool)
            commit()
        }
    }

    fun setNotificationsVisibilityPreference(bool: Boolean) {
        with (sharedPref.edit()){
            putBoolean(NOTIFICATIONS_VISIBILITY, bool)
            commit()
        }
    }
}