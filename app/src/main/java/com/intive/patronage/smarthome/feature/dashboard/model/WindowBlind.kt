package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class WindowBlind(
    @Json(name = "position") var position: Int
) : BaseHomeSensor()