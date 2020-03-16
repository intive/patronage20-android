package com.intive.patronage.smarthome.dashboard.model.api.service

import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.logic.convertHSVtoRGB
import com.intive.patronage.smarthome.dashboard.model.*
import java.lang.StringBuilder

fun transformFromLights(lights: List<Light>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    lights.forEach {
        sensors.add(
            DashboardSensor(
                it.id.toString(),
                it.type,
                convertHSVtoRGB(it.hue, it.saturation, it.value).toString()
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
                ""
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
                details.append(R.string.hvac_room_heating_temperature)
                    .append(it.heatingTemperature)
                    .append(R.string.transformer_separator)
                    .append(R.string.hvac_room_cooling_temperature)
                    .append(it.coolingTemperature)
                    .append(R.string.transformer_separator)
                    .append(R.string.hvac_room_hysteresis)
                    .append(it.hysteresis)
                    .toString()

            )
        )
    }
    return sensors
}


fun transfromFromHVACStatus(hvaStatus: List<HVACStatus>): List<DashboardSensor> {
    val sensors = mutableListOf<DashboardSensor>()
    hvaStatus.forEach {
        val details = StringBuilder()
        if (it.heating) {
            details.append(R.string.hvac_status_heating)
        } else {
            details.append(R.string.hvac_status_cooling)
        }
        sensors.add(
            DashboardSensor(
                0.toString(),
                it.type,
                details.toString()
            )
        )
    }
    return sensors
}
