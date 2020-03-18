package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class SmokeSensor(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "isSmokeDetected") val isSmokeDetected: Boolean
)