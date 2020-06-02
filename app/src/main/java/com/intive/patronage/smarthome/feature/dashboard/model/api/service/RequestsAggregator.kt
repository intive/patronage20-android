package com.intive.patronage.smarthome.feature.dashboard.model.api.service

import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard

private var requestCounter: Int = 0
private val sumOfTemperatureValues = mutableMapOf<Int, Float>()
private const val valuesAmount = 60

fun aggregateRequests(dashboard: Dashboard): Dashboard? {
    requestCounter++

    if (requestCounter == valuesAmount) {
        dashboard.temperatureSensors?.forEach {
            it.value = if (sumOfTemperatureValues.contains(it.id)) {
                (sumOfTemperatureValues.getValue(it.id) + it.value) / valuesAmount
            } else {
                it.value
            }
        }

        requestCounter = 0
        sumOfTemperatureValues.forEach {
            sumOfTemperatureValues[it.key] = 0f
        }

        return dashboard
    } else {
        dashboard.temperatureSensors?.forEach {
            val sum: Float? = if (sumOfTemperatureValues.contains(it.id)) {
                sumOfTemperatureValues.getValue(it.id)
            } else {
                0f
            }

            if (sum != null) {
                sumOfTemperatureValues[it.id] = sum + it.value
            } else {
                sumOfTemperatureValues[it.id] = it.value
            }
        }

        return null
    }
}