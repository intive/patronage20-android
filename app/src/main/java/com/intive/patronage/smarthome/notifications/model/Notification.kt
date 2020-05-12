package com.intive.patronage.smarthome.notifications.model

import com.squareup.moshi.Json

data class Notification(
    @Json(name = "id") val id: Int,
    @Json(name = "timestamp") val timestamp: Long,
    @Json(name = "type") val type: String,
    @Json(name = "sensorId") val sensorId: Int,
    @Json(name = "isChecked") val isChecked: Boolean
)