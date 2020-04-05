package com.intive.patronage.smarthome.feature.dashboard.model.api.service

import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.feature.dashboard.model.*
import com.intive.patronage.smarthome.feature.dashboard.model.api.respository.DashboardRepositoryAPI
import com.intive.patronage.smarthome.feature.dashboard.model.api.room.repository.DashboardRoomRepositoryAPI
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import java.util.concurrent.TimeUnit

const val intervalDelay = 100L
const val valuesAmount = 10 // 600

class DashboardService(
    private val smartHomeAPI: SmartHomeAPI,
    private val dashboardRepository: DashboardRepositoryAPI,
    private val dashboardRoomRepository: DashboardRoomRepositoryAPI
) {
    private var requestCounter: Int = 0
    private val sumOfTemperatureValues = mutableMapOf<Int, Int>()

    fun getDashboard(): Single<Dashboard> = dashboardRepository.getDashboard()
        .switchIfEmpty(getDashboardFromNetwork())

    fun getRoomDashboards() = dashboardRoomRepository.getAllDashboards()

    private fun getDashboardFromNetwork(): Single<Dashboard> {
        return smartHomeAPI.getDashboard().doOnSuccess { dashboard ->
            dashboardRepository.setDashboard(dashboard)
            requestCounter++
            if (requestCounter == (valuesAmount + 1)) {
                dashboard.temperatureSensors.forEach {
                    it.value = if (sumOfTemperatureValues.contains(it.id)) {
                        sumOfTemperatureValues.getValue(it.id) / valuesAmount
                    } else {
                        it.value
                    }
                }

                dashboardRoomRepository.insertDashboard(dashboard)

                requestCounter = 0
                sumOfTemperatureValues.forEach {
                    sumOfTemperatureValues[it.key] = 0
                }
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
            }
        }
    }

    fun fetchDashboardWithDelay(minWaitTime: Long, maxWaitTime: Long)
            : Observable<Pair<Dashboard, Long>> =
        getDashboardFromNetwork().toObservable()
            .zipWith(Observable.timer(minWaitTime, TimeUnit.SECONDS))
            .timeout(maxWaitTime, TimeUnit.SECONDS)

    private fun provideDashboardSensors(source: Single<Dashboard>): Observable<List<DashboardSensor>> {
        return source.map { transformSensors(it) }
            .toObservable()
    }

    fun fetchSensorsInInterval(): Observable<List<DashboardSensor>> =
        Observable.interval(intervalDelay, intervalDelay, TimeUnit.MILLISECONDS)
            .flatMap { provideDashboardSensors(getDashboardFromNetwork()) }
            .startWith( provideDashboardSensors( getDashboard()) )


    private fun transformSensors(dashboard: Dashboard): List<DashboardSensor> {
        val sensors = mutableListOf<DashboardSensor>()
        sensors.addAll(transformFromLights(dashboard.lights))
        sensors.addAll(transformFromTemperatureSensors(dashboard.temperatureSensors))
        sensors.addAll(transformFromSmokeSensors(dashboard.smokeSensors))
        sensors.addAll(transformFromWindowBlinds(dashboard.windowBlinds))
        sensors.addAll(transfromFromWindowSensors(dashboard.windowSensors))
        sensors.addAll(transformFromRFIDSensors(dashboard.RFIDSensors))
        sensors.addAll(transformFromHVACRooms(dashboard.HVACRooms))
        // TODO: verify when API ready
        // sensors.addAll(transfromFromHVACStatus(dashboard.HVACStatus))
        return sensors.toList()
    }

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