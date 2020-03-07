package com.intive.patronage.smarthome.dashboard.model

import com.squareup.moshi.Json

data class WindowBlind(
    @Json(name = "id") val id: Int,
    @Json(name = "type") val type: String,
    @Json(name = "position") val position: Int
)