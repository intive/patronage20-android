package com.intive.patronage.smarthome.feature.light.view

interface ColorPickerEventListener {
    fun setBrightnessSeekBarColor(red: Int, green: Int, blue: Int)
    fun setCurrentImageViewColor(red: Int, green: Int, blue: Int)
}