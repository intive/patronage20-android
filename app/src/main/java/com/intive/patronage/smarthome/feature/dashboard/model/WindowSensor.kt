package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class WindowSensor(
    @Json(name = "status") val status: String
) : BaseHomeSensor()