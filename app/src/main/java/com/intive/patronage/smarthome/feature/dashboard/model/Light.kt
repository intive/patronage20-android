package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class Light(
    @Json(name = "hue") var hue: Int,
    @Json(name = "saturation") var saturation: Int,
    @Json(name = "value") var value: Int
) : BaseHomeSensor()