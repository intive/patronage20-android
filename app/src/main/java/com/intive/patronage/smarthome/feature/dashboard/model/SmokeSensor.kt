package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class SmokeSensor(
    @Json(name = "isSmokeDetected") val isSmokeDetected: Boolean
) : BaseHomeSensor()