package com.intive.patronage.smarthome.feature.blind.model.api

import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.feature.blind.model.BlindSensor

class BlindDetailsService(private val smartHomeAPI: SmartHomeAPI) {

    fun updateBlindPosition(body: BlindSensor) = smartHomeAPI.updateBlindPosition(body)
}