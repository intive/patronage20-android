package com.intive.patronage.smarthome.feature.home.model.api.service

import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor

class HomeService(private val smartHomeAPI: SmartHomeAPI) {

    fun postSensor(id: Int, body: HomeSensor) = smartHomeAPI.addSensor(id, body)

    fun deleteSensor(id: Int) = smartHomeAPI.deleteSensor(id)

}
