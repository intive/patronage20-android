package com.intive.patronage.smarthome.feature.hvac.viewmodel

interface HVACViewEventListener {
    fun setTemperature(temperature :Float)
    fun setHysteresis(hysteresis : Int)
    fun connectionError(error: Boolean)
    fun setHeatingTemperature(heatingTemperature :Int)
    fun setCoolingTemperature(coolingTemperature :Int)
    fun showToast(message: Int)
    fun resetSetting()
}