package com.intive.patronage.smarthome.feature.light.model.api

import com.intive.patronage.smarthome.api.SmartHomeAPI

class LightDetailsService(private val smartHomeAPI: SmartHomeAPI) {

    fun changeLightColor(light: LightDTO) = smartHomeAPI.putLight(light)
}