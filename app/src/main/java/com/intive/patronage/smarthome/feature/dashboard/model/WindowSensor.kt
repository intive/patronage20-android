package com.intive.patronage.smarthome.feature.dashboard.model

import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.squareup.moshi.Json

data class WindowSensor(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "isOpen") val isOpen: Boolean,
    @Json(name = "mapPosition") val _mapPosition: MapPosition?
) : HomeSensor(id, type, _mapPosition)