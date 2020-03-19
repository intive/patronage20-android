package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class Dashboard(
    @Json(name = "temperatureSensors") val temperatureSensors: List<TemperatureSensor>,
    @Json(name = "windowSensors") val windowSensors: List<WindowSensor>,
    @Json(name = "windowBlinds") val windowBlinds: List<WindowBlind>,
    @Json(name = "RFIDSensors") val RFIDSensors: List<RFIDSensor>,
    @Json(name = "smokeSensors") val smokeSensors: List<SmokeSensor>,
    @Json(name = "HVACStatus") val HVACStatus: HVACStatus,
    @Json(name = "HVACRooms") val HVACRooms: List<HVACRoom>,
    @Json(name = "lights") val lights: List<Light>
)