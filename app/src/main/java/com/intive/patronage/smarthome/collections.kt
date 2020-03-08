package com.intive.patronage.smarthome

fun <T> MutableList<T>.replace(sensorsList: List<T>){
    this.clear()
    this.addAll(sensorsList)
}