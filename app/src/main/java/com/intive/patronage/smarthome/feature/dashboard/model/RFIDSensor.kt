package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class RFIDSensorLastTag(
    @Json(name = "type") val type: String
)

data class RFIDSensor(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "lastTag") val lastTag: RFIDSensorLastTag,
    @Json(name = "mapPosition") val mapPosition: MapPosition?
) : BaseHomeSensor(id, type, mapPosition)