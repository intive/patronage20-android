package com.intive.patronage.smarthome.feature.light.view

interface ColorPickerEventListener {
    fun setBrightnessBarColor(red: Int, green: Int, blue: Int)
    fun setCurrentImageViewColor(red: Int, green: Int, blue: Int)
    fun setColorPickerPointerPosition(x: Float, y: Float)
    fun setBrightnessBarPointerPosition(x: Float)
    fun resetPointersPosition()
    fun showToast(message: Int)
}