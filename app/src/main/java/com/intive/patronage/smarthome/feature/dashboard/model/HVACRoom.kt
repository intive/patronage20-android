package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class HVACRoom(
    @field:Json(name = "heatingTemperature") var heatingTemperature: Int,
    @field:Json(name = "coolingTemperature") var coolingTemperature: Int,
    @Json(name = "hysteresis") var hysteresis: Int,
    @Json(name = "temperatureSensorId") var temperatureSensorId: Int,
    @Json(name = "windowSensorIds") val windowSensorIds: List<Int>
) : BaseHomeSensor()