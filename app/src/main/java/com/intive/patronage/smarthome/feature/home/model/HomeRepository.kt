package com.intive.patronage.smarthome.feature.home.model

import com.intive.patronage.smarthome.feature.home.view.DialogSensorMock

class HomeRepository {
    val sensorList = mutableListOf(
        DialogSensorMock("temperatureSensor", 13f, 50f, true)
        , DialogSensorMock("windowSensor", -1f, -1f, false)
        , DialogSensorMock("windowBlind", 13f, 61f, true)
        , DialogSensorMock("RFIDSensor", -1f, -1f, false)
        , DialogSensorMock("smokeSensor", -1f, -1f, false)
        , DialogSensorMock("RGBLight", -1f, -1f, false)
        , DialogSensorMock("windowSensor", -1f, -1f, false)
        , DialogSensorMock("RGBLight", -1f, -1f, false)
        , DialogSensorMock("temperatureSensor", -1f, -1f, false)
        , DialogSensorMock("temperatureSensor", -1f, -1f, false)
        , DialogSensorMock("temperatureSensor", -1f, -1f, false)
        , DialogSensorMock("temperatureSensor", -1f, -1f, false)
        , DialogSensorMock("temperatureSensor", -1f, -1f, false)
        , DialogSensorMock("temperatureSensor", -1f, -1f, false)
        , DialogSensorMock("windowBlind", -1f, -1f, false)
    )
}