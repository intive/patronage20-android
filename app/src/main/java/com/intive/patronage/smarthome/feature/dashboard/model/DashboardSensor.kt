package com.intive.patronage.smarthome.feature.dashboard.model

import java.io.Serializable

data class DashboardSensor(
    var id: String,
    var type: String,
    var details: String,
    val mapPosition: MapPosition?
): Serializable