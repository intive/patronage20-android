package com.intive.patronage.smarthome.feature.blind.view

interface BlindViewEventListener {
    fun blindUp()
    fun blindDown()
    fun setStartingPosition(percentPosition: Int)
    fun showToast(message: Int)
}