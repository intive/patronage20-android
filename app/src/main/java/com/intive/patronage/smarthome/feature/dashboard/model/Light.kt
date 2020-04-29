package com.intive.patronage.smarthome.feature.dashboard.model

import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.squareup.moshi.Json

data class Light(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "hue") val hue: Int,
    @Json(name = "saturation") val saturation: Int,
    @Json(name = "value") val value: Int,
    @Json(name = "mapPosition") val _mapPosition: MapPosition?
) : HomeSensor(id, type, _mapPosition)