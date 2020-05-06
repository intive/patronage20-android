package com.intive.patronage.smarthome.feature.settings

import android.content.res.Configuration
import android.content.res.Resources
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.intive.patronage.smarthome.common.PreferencesWrapper

fun setupDarkModeSwitch(preferencesWrapper: PreferencesWrapper, switch: Switch, resources: Resources) {
    val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
        switch.isChecked = true
    }
    switch.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            setDarkMode(true, preferencesWrapper)
        } else {
            setDarkMode(false, preferencesWrapper)
        }
    }
}

private fun setDarkMode(darkMode: Boolean, preferencesWrapper: PreferencesWrapper) {
    if (darkMode) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    preferencesWrapper.setDarkModePreference(darkMode)
}