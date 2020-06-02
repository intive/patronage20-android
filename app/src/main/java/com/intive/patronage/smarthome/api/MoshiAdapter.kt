package com.intive.patronage.smarthome.api

import com.intive.patronage.smarthome.feature.dashboard.model.HVACRoom
import com.intive.patronage.smarthome.feature.dashboard.model.Light
import com.intive.patronage.smarthome.feature.dashboard.model.TemperatureSensor
import com.intive.patronage.smarthome.feature.dashboard.model.WindowBlind
import com.squareup.moshi.FromJson
import kotlin.random.Random

class TemperatureAdapter {
    @FromJson
    fun fromJson(temperature: TemperatureSensor): TemperatureSensor = temperature.apply {
        value = Random.nextInt(31) + 10 + Random.nextFloat()
    }
}

class HVACRoomAdapter {
    @FromJson
    fun fromJson(hvacRoom: HVACRoom): HVACRoom = hvacRoom.apply {
        heatingTemperature = 100
        coolingTemperature = 200
        hysteresis = 10
        temperatureSensorId = 61
    }
}

class WindowBlindAdapter {
    @FromJson
    fun fromJson(windowBlind: WindowBlind): WindowBlind = windowBlind.apply {
        position = 40
    }
}

class LightAdapter {
    @FromJson
    fun fromJson(light: Light): Light = light.apply {
        hue = 40
        saturation = 85
        value = 100
    }
}