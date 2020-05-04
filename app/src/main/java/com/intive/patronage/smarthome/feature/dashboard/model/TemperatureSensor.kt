package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class TemperatureSensor(
    @Json(name = "value") var value: Int
) : BaseHomeSensor()