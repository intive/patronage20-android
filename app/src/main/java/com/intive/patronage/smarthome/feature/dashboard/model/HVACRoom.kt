package com.intive.patronage.smarthome.feature.dashboard.model

import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.squareup.moshi.Json

data class HVACRoom(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @field:Json(name = "heating-temperature") val heatingTemperature: Int,
    @field:Json(name = "cooling-temperature") val coolingTemperature: Int,
    @Json(name = "hysteresis") val hysteresis: Int,
    @Json(name = "temperatureSensorId") val temperatureSensorId: Int,
    @Json(name = "windowSensorIds") val windowSensorIds: List<Int>,
    @Json(name = "mapPosition") val _mapPosition: MapPosition?
) : HomeSensor(id, type, _mapPosition)