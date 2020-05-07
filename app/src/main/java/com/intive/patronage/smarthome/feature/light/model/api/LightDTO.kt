package com.intive.patronage.smarthome.feature.light.model.api

import com.squareup.moshi.Json

data class LightDTO (
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "hue") val hue: Int,
    @Json(name = "saturation") val saturation: Int,
    @Json(name = "value") val value: Int
)