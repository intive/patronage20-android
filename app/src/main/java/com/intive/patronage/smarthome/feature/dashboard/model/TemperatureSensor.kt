package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class TemperatureSensor(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "value") var value: Int,
    @Json(name = "mapPosition") val mapPosition: MapPosition?
)