package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class HVACStatus(
    @Json(name = "heating") val heating: Boolean,
    @Json(name = "cooling") val cooling: Boolean
)