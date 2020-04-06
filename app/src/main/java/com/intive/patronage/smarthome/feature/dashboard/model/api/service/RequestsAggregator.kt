package com.intive.patronage.smarthome.feature.dashboard.model.api.service

import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard

private var requestCounter: Int = 0
private val sumOfTemperatureValues = mutableMapOf<Int, Int>()
private const val valuesAmount = 10 // 600

fun aggregateRequests(dashboard: Dashboard): Dashboard? {
    requestCounter++

    if (requestCounter == valuesAmount) {
        dashboard.temperatureSensors.forEach {
            it.value = if (sumOfTemperatureValues.contains(it.id)) {
                (sumOfTemperatureValues.getValue(it.id) + it.value) / valuesAmount
            } else {
                it.value
            }
        }

        requestCounter = 0
        sumOfTemperatureValues.forEach {
            sumOfTemperatureValues[it.key] = 0
        }

        return dashboard
    } else {
        dashboard.temperatureSensors.forEach {
            val sum: Int? = if (sumOfTemperatureValues.contains(it.id)) {
                sumOfTemperatureValues.getValue(it.id)
            } else {
                0
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