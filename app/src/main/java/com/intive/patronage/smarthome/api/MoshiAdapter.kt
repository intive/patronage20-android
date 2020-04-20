package com.intive.patronage.smarthome.api

import android.view.Window
import com.intive.patronage.smarthome.feature.dashboard.model.HVACRoom
import com.intive.patronage.smarthome.feature.dashboard.model.Light
import com.intive.patronage.smarthome.feature.dashboard.model.TemperatureSensor
import com.intive.patronage.smarthome.feature.dashboard.model.WindowBlind
import com.squareup.moshi.FromJson

class TemperatureAdapter{
    @FromJson
    fun fromJson(temperature: TemperatureSensor): TemperatureSensor {
        return TemperatureSensor(temperature.id, temperature.type, 10, temperature.mapPosition)
    }
}

class HVACRoomAdapter{
    @FromJson
    fun fromJson(hvacRoom: HVACRoom): HVACRoom {
        return HVACRoom(hvacRoom.id, hvacRoom.type, 100, 200, 10, hvacRoom.temperatureSensorId, hvacRoom.windowSensorIds, hvacRoom.mapPosition)
    }
}

class WindowBlindAdapter{
    @FromJson
    fun fromJson(windowBlind: WindowBlind): WindowBlind {
        return WindowBlind(windowBlind.id, windowBlind.type, 40, windowBlind.mapPosition)
    }
}

class LightAdapter{
    @FromJson
    fun fromJson(light: Light): Light {
        return Light(light.id, light.type, 40, 85, 100, light.mapPosition)
    }
}