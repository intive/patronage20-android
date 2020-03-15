package com.intive.patronage.smarthome.dashboard.model.api.service

import com.intive.patronage.smarthome.dashboard.model.*

fun transformFromLights(lights: List<Light>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    lights.forEach {
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                it.hue.toString() //change to hex value
            )
        )
    }
//    Log.d("XDD", sensors.toString())
    return sensors
}

fun transformFromTemperatureSensors(temperatureSensors: List<TemperatureSensor>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    temperatureSensors.forEach {
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                it.value.toString()
            )
        )
    }
    return sensors
}

fun transformFromSmokeSensors(smokeSensors: List<SmokeSensor>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    smokeSensors.forEach {
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                it.isSmokeDetected.toString()
            )
        )
    }
    return sensors
}

fun transformFromWindowBlinds(windowBlinds: List<WindowBlind>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    windowBlinds.forEach {
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                it.position.toString()
            )
        )
    }
    return sensors
}

fun transfromFromWindowSensors(windowSensors: List<WindowSensor>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    windowSensors.forEach {
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                it.status.toString()
            )
        )
    }
    return sensors
}

fun transformFromRFIDSensors(rfidSensors: List<RFIDSensor>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    rfidSensors.forEach {
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                it.lastTag.toString()
            )
        )
    }
    return sensors
}

fun transformFromHVACRooms(hvacRooms: List<HVACRoom>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    hvacRooms.forEach {
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                it.heatingTemperature.toString() // tbd
            )
        )
    }
    return sensors
}

//hvac status