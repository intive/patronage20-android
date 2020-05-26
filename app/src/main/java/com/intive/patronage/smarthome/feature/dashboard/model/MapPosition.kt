package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json
import java.io.Serializable

data class MapPosition(@Json(name = "x") val x: Float,
                       @Json(name = "y") val y: Float): Serializable

