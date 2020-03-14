package com.intive.patronage.smarthome.dashboard.model

import com.squareup.moshi.Json

enum class WindowSensorStatus {
    @Json(name = "open") OPEN,
    @Json(name = "closed") CLOSED
}

data class WindowSensor(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "status") val status: WindowSensorStatus
)