package com.intive.patronage.smarthome.feature.dashboard.model.api.service

import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.convertHSVtoRGB
import com.intive.patronage.smarthome.feature.dashboard.model.*
import java.lang.StringBuilder

const val TRANSFORMER_SEPARATOR = ","

fun transformFromLights(lights: List<Light>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    lights.forEach {
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                convertHSVtoRGB(
                    it.hue,
                    it.saturation,
                    it.value
                ).toString(),
                it.mapPosition
            )
        )
    }
    return sensors
}

fun transformFromTemperatureSensors(temperatureSensors: List<TemperatureSensor>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    temperatureSensors.forEach {
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                it.value.toString(),
                it.mapPosition
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
                it.isSmokeDetected.toString(),
                it.mapPosition
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
                it.position.toString(),
                it.mapPosition
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
                it.isOpen.toString(),
                it.mapPosition
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
                "",
                it.mapPosition
            )
        )
    }
    return sensors
}

fun transformFromHVACRooms(hvacRooms: List<HVACRoom>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    hvacRooms.forEach {
        val details = StringBuilder()
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                details.append(it.heatingTemperature)
                    .append(TRANSFORMER_SEPARATOR)
                    .append(it.coolingTemperature)
                    .append(TRANSFORMER_SEPARATOR)
                    .append(it.hysteresis)
                    .toString(),
                it.mapPosition
            )
        )
    }
    return sensors
}

fun transfromFromHVACStatus(hvacStatus: HVACStatus): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    val details = StringBuilder()
    if (hvacStatus.heating) {
        details.append(R.string.hvac_status_heating)
    } else {
        details.append(R.string.hvac_status_cooling)
    }
    sensors.add(
        DashboardSensor(
            0.toString(),
            "",
            details.toString(),
            null
        )
    )
    return sensors
}