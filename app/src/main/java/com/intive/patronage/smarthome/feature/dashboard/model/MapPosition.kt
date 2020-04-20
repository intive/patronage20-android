package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json

data class MapPosition(@Json(name = "x") val x: Int, @Json(name = "y") val y: Int)