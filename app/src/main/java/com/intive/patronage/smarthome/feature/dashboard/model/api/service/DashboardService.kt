package com.intive.patronage.smarthome.feature.dashboard.model.api.service

import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.feature.dashboard.model.*
import com.intive.patronage.smarthome.feature.dashboard.model.api.respository.DashboardRepositoryAPI
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

    private fun getDashboardSensors(): Observable<List<DashboardSensor>> {
        return smartHomeAPI.getDashboard()
            .map {
                val sensors = mutableListOf<DashboardSensor>()
                sensors.addAll(transformFromLights(it.lights))
                sensors.addAll(transformFromTemperatureSensors(it.temperatureSensors))
                sensors.addAll(transformFromSmokeSensors(it.smokeSensors))
                sensors.addAll(transformFromWindowBlinds(it.windowBlinds))
                sensors.addAll(transfromFromWindowSensors(it.windowSensors))
                sensors.addAll(transformFromRFIDSensors(it.RFIDSensors))
                sensors.addAll(transformFromHVACRooms(it.HVACRooms))
                //add hvac status
                sensors.toList()
            }.toObservable()
    }

    fun updateSensors(): Observable<List<DashboardSensor>> =
        Observable.interval(0, 10, TimeUnit.SECONDS)
            .flatMap { getDashboardSensors() }

    fun getLightById(id: Int): Single<Light?> {
        return dashboardRepository.getDashboard()
            .map { dashboard ->
                var singleLight: Light? = null
                dashboard.lights.forEach { light ->
                    if (light.id == id) singleLight = light
                }
                singleLight
            }.toSingle()
    }

    fun getHVACById(id: Int): Single<HVACRoom?> {
        return dashboardRepository.getDashboard()
            .map { dashboard ->
                var singleHVACRoom: HVACRoom? = null
                dashboard.HVACRooms.forEach { room ->
                    if (room.id == id) singleHVACRoom = room
                }
                singleHVACRoom
            }.toSingle()

    }

    fun getBlindById(id: Int): Single<WindowBlind?> {
        return dashboardRepository.getDashboard()
            .map { dashboard ->
                var singleBlind: WindowBlind? = null
                dashboard.windowBlinds.forEach { blind ->
                    if (blind.id == id) singleBlind = blind
                }
                singleBlind
            }.toSingle()
    }

    fun getTemperatureSensorById(id: Int): Single<TemperatureSensor?> {
        return dashboardRepository.getDashboard()
            .map { dashboard ->
                var singleTemeraureSensor: TemperatureSensor? = null
                dashboard.temperatureSensors.forEach { senor ->
                    if (senor.id == id) singleTemeraureSensor = senor
                }
                singleTemeraureSensor
            }.toSingle()
    }
}