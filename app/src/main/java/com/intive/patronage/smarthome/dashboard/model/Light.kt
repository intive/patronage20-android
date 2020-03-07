package com.intive.patronage.smarthome.dashboard.model

import com.squareup.moshi.Json

data class Light(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "hue") val hue: Int,
    @Json(name = "saturation") val saturation: Int,
    @Json(name = "id") val value: Int
)