package com.intive.patronage.smarthome.feature.hvac.model.api


import com.squareup.moshi.Json

data class HVACRoomDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @field:Json(name = "heatingTemperature") var heatingTemperature: Int,
    @field:Json(name = "coolingTemperature") var coolingTemperature: Int,
    @Json(name = "hysteresis") var hysteresis: Int,
    @Json(name = "temperatureSensorId") var temperatureSensorId: Int,
    @Json(name = "windowSensorIds") val windowSensorIds: List<Int>
)