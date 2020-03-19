package com.intive.patronage.smarthome.common

fun <T> MutableList<T>.replace(sensorsList: List<T>) {
    this.clear()
    this.addAll(sensorsList)
}