package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class HVACRoom(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "heating-temperature") val heatingTemperature: Int,
    @Json(name = "cooling-temperature") val coolingTemperature: Int,
    @Json(name = "hysteresis") val hysteresis: Int,
    @Json(name = "temperatureSensorId") val temperatureSensorId: Int,
    @Json(name = "windowSensorIds") val windowSensorIds: List<Int>
)