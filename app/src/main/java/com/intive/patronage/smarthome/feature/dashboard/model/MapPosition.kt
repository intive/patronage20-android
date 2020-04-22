package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class MapPosition(@Json(name = "x") val x: Float,
                       @Json(name = "y") val y: Float)