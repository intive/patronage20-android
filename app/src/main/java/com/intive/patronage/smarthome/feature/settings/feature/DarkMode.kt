package com.intive.patronage.smarthome.feature.settings.feature

import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import com.intive.patronage.smarthome.common.PreferencesWrapper
import com.intive.patronage.smarthome.feature.settings.SettingType

fun setupDarkModeSwitch(resources: Resources) {
    val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
        SettingType.NIGHT_MODE.isChecked = true
    }
}

fun setDarkMode(darkMode: Boolean, preferencesWrapper: PreferencesWrapper) {
    if (darkMode) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    preferencesWrapper.setDarkModePreference(darkMode)
}