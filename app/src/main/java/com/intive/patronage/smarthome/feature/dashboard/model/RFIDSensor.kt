package com.intive.patronage.smarthome.feature.dashboard.model

import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.squareup.moshi.Json

data class RFIDSensorLastTag(
    @Json(name = "type") val type: String
)

data class RFIDSensor(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "lastTag") val lastTag: RFIDSensorLastTag,
    @Json(name = "mapPosition") val _mapPosition: MapPosition?
) : HomeSensor(id, type, _mapPosition)