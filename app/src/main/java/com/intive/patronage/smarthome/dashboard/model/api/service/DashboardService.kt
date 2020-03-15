package com.intive.patronage.smarthome.dashboard.model.api.service

import android.annotation.SuppressLint
import android.util.Log
import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.dashboard.model.Dashboard
import com.intive.patronage.smarthome.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.dashboard.model.api.respository.DashboardRepositoryAPI
import com.intive.patronage.smarthome.di.dashboardApiModule
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class DashboardService(
    private val smartHomeAPI: SmartHomeAPI,
    private val dashboardRepository: DashboardRepositoryAPI
) {

    fun getDashboard(): Single<Dashboard> = dashboardRepository.getDashboard()
        .switchIfEmpty(getDashboardFromNetwork())

    private fun getDashboardFromNetwork(): Single<Dashboard> {
        return smartHomeAPI.getDashboard().doOnSuccess {
            dashboardRepository.setDashboard(it)
        }
    }

    @SuppressLint("CheckResult")
    fun getDashboardSensors(): List<DashboardSensor> {
        val sensors = mutableListOf<DashboardSensor>()
        dashboardRepository.getDashboard()
            .map {
                it.lights.forEach {
                    sensors.add(
                        DashboardSensor(
                            it.id.toString(),
                            it.type,
                            it.hue.toString() //change to hex value
                        )
                    )
                }

                it.temperatureSensors.forEach {
                    sensors.add(
                        DashboardSensor(
                            it.id.toString(),
                            it.type,
                            it.value.toString()
                        )
                    )
                }

                it.smokeSensors.forEach {
                    sensors.add(
                        DashboardSensor(
                            it.id.toString(),
                            it.type,
                            it.isSmokeDetected.toString()
                        )
                    )
                }

                it.windowBlinds.forEach {
                    sensors.add(
                        DashboardSensor(
                            it.id.toString(),
                            it.type,
                            it.position.toString()
                        )
                    )
                }

                it.windowSensors.forEach {
                    sensors.add(
                        DashboardSensor(
                            it.id.toString(),
                            it.type,
                            it.status.toString()
                        )
                    )
                }

                it.RFIDSensors.forEach {
                    sensors.add(
                        DashboardSensor(
                            it.id.toString(),
                            it.type,
                            it.lastTag.toString()
                        )
                    )
                }

                it.HVACRooms.forEach {
                    sensors.add(
                        DashboardSensor(
                            it.id.toString(),
                            it.type,
                            it.heatingTemperature.toString() // tbd
                        )
                    )
                }

                //add hvac status
            }.subscribe()
        return sensors
    }
}