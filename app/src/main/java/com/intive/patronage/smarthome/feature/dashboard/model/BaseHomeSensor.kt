package com.intive.patronage.smarthome.feature.dashboard.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class BaseHomeSensor(
    @Json(name = "id") var id: Int = 0,
    @Json(name = "type") var type: String = "",
    @Json(name = "mapPosition") var mapPosition: MapPosition? = null
)