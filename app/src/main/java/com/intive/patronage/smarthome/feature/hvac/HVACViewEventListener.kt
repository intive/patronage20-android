package com.intive.patronage.smarthome.feature.hvac

interface HVACViewEventListener {
    fun setTemperature(temperature :Float)
    fun setHysteresis(hysteresis : Float)
    fun connectionError(error: Boolean)
    fun setHeatingTemperature(heatingTemperature :Int)
    fun setCoolingTemperature(coolingTemperature :Int)
}