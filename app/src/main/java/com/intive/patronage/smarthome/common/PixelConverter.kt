package com.intive.patronage.smarthome.common

fun percentToCoordinateX(value: Float, imageWidth: Int): Float {
    return (imageWidth / 100f * value)
}

fun percentToCoordinateY(value: Float, imageHeight: Int): Float {
    return (imageHeight / 100f * value)
}

fun coordinateToPercentX(value: Float, imageWidth: Int): Float {
    return (value / imageWidth * 100f)
}

fun coordinateToPercentY(value: Float, imageHeight: Int): Float {
    return (value / imageHeight * 100f)
}