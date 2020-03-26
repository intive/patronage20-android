package com.intive.patronage.smarthome.feature.developer.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.DeveloperSettings

class DeveloperSettingsViewModel(
    private val developerSettings: DeveloperSettings,
    var version : ObservableField<String>) : ViewModel() {

     init {
         version.apply {
             this.set(developerSettings.getVersion())
         }
     }

    fun isDebugMode() = developerSettings.isDebugMode()
}