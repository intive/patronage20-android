package com.intive.patronage.smarthome.feature.developer.model

import com.intive.patronage.smarthome.api.SmartHomeAPI

class DeveloperSettingsService(
    private val smartHomeAPI: SmartHomeAPI) {

    fun deleteAllSensors() = smartHomeAPI.deleteSensors()
}