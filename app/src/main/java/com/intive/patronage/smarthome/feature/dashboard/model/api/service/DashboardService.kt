package com.intive.patronage.smarthome.feature.dashboard.model.api.service

import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.feature.dashboard.model.*
import com.intive.patronage.smarthome.feature.dashboard.model.api.respository.DashboardRepositoryAPI
import com.intive.patronage.smarthome.feature.dashboard.model.api.room.repository.DashboardRoomRepository
import com.intive.patronage.smarthome.feature.dashboard.model.api.room.repository.DashboardRoomRepositoryAPI
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class DashboardService(
    private val smartHomeAPI: SmartHomeAPI,
    private val dashboardRepository: DashboardRepositoryAPI,
    private val dashboardRoomRepository: DashboardRoomRepositoryAPI
) {

    fun getDashboard(): Single<Dashboard> = dashboardRepository.getDashboard()
        .switchIfEmpty(getDashboardFromNetwork())

    private fun getDashboardFromNetwork(): Single<Dashboard> {
        return smartHomeAPI.getDashboard().doOnSuccess {
            dashboardRepository.setDashboard(it)
            dashboardRoomRepository.insertDashboard(it)
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
            .flatMapObservable { Observable.fromIterable(it.lights) }
            .filter { it.id == id }
            .firstOrError()
    }

    fun getHVACById(id: Int): Single<HVACRoom?> {
        return dashboardRepository.getDashboard()
            .flatMapObservable { Observable.fromIterable(it.HVACRooms) }
            .filter{it.id == id}
            .firstOrError()
    }

    fun getBlindById(id: Int): Single<WindowBlind?> {
        return dashboardRepository.getDashboard()
            .flatMapObservable { Observable.fromIterable(it.windowBlinds) }
            .filter { it.id == id }
            .firstOrError()
    }

    fun getTemperatureSensorById(id: Int): Single<TemperatureSensor?> {
        return dashboardRepository.getDashboard()
            .flatMapObservable { Observable.fromIterable(it.temperatureSensors) }
            .filter { it.id==id }
            .firstOrError()

    }
}