package com.intive.patronage.smarthome.feature.home.model.api.service

import com.intive.patronage.smarthome.feature.dashboard.model.*
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor

fun transformFromLights(lights: List<Light>): List<HomeSensor> {
    val sensors = mutableListOf<HomeSensor>()
    lights.forEach {
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

fun transformFromTemperatureSensors(temperatureSensors: List<TemperatureSensor>): List<HomeSensor> {
    val sensors = mutableListOf<HomeSensor>()
    temperatureSensors.forEach {
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

fun transformFromSmokeSensors(smokeSensors: List<SmokeSensor>): List<HomeSensor> {
    val sensors = mutableListOf<HomeSensor>()
    smokeSensors.forEach {
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

fun transformFromWindowBlinds(windowBlinds: List<WindowBlind>): List<HomeSensor> {
    val sensors = mutableListOf<HomeSensor>()
    windowBlinds.forEach {
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

fun transfromFromWindowSensors(windowSensors: List<WindowSensor>): List<HomeSensor> {
    val sensors = mutableListOf<HomeSensor>()
    windowSensors.forEach {
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

fun transformFromRFIDSensors(rfidSensors: List<RFIDSensor>): List<HomeSensor> {
    val sensors = mutableListOf<HomeSensor>()
    rfidSensors.forEach {
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

fun transformFromHVACRooms(hvacRooms: List<HVACRoom>): List<HomeSensor> {
    val sensors = mutableListOf<HomeSensor>()
    hvacRooms.forEach {
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

fun transfromFromHVACStatus(hvacStatus: HVACStatus): List<HomeSensor> {
    val sensors = mutableListOf<HomeSensor>()
//    sensors.add(
//        HomeSensor(
//            it.id.toString(),
//            it.type,
//            it.mapPosition
//        )
//    )
    return sensors
}