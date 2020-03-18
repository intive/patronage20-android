package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class HVACStatus(
    @Json(name = "type") val type: String,
    @Json(name = "heating") val heating: Boolean,
    @Json(name = "cooling") val cooling: Boolean
)