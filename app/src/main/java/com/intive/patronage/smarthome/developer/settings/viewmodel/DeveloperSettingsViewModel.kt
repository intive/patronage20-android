package com.intive.patronage.smarthome.developer.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.developer.settings.DeveloperSettings

class DeveloperSettingsViewModel(val developerSettings: DeveloperSettings) : ViewModel() {

    fun versionApp() : String {
        return developerSettings.versionApp
    }

    fun loadURLEndpoints() {

    }
}