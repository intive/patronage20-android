package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class RFIDSensorLastTag(
    @Json(name = "id") val id: Long,
    @Json(name = "type") val type: String,
    @Json(name = "timestamp") val timestamp: Long
)

data class RFIDSensor(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "lastTag") val lastTag: RFIDSensorLastTag
)