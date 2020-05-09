package com.intive.patronage.smarthome.feature.home.model.api

import com.intive.patronage.smarthome.feature.dashboard.model.MapPosition

open class HomeSensor(
    var _id: Int,
    var sensorType: String,
    var mapPosition: MapPosition?
)