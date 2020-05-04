package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class RFIDSensorLastTag(
    @Json(name = "type") val type: String
)

data class RFIDSensor(
    @Json(name = "lastTag") val lastTag: RFIDSensorLastTag
) : BaseHomeSensor()