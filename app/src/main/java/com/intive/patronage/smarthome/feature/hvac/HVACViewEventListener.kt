package com.intive.patronage.smarthome.feature.hvac

interface HVACViewEventListener {
    fun setTemperature(temperature :Float)
    fun setHysteresis(hysteresis : Float)
    fun connectionError(error: Boolean)
}