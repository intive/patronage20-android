package com.intive.patronage.smarthome.feature.home.model.api.service

import com.intive.patronage.smarthome.feature.dashboard.model.BaseHomeSensor
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor

fun transformFromBaseSensors(baseSensors: List<BaseHomeSensor>): List<HomeSensor> {
    val sensors = mutableListOf<HomeSensor>()
    baseSensors.forEach {
        sensors.add(
            HomeSensor(
                it.id,
                it.type,
                it.mapPosition
            )
        )
    }
    return sensors
}