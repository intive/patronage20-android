package com.intive.patronage.smarthome.feature.home.model.api

import com.intive.patronage.smarthome.feature.dashboard.model.MapPosition

data class HomeSensor(
    var id: String,
    var type: String,
    val mapPosition: MapPosition?
)