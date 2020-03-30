package com.intive.patronage.smarthome.feature.dashboard.model.api.service

import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.model.Light
import com.intive.patronage.smarthome.feature.dashboard.model.WindowBlind
import com.intive.patronage.smarthome.feature.dashboard.model.api.respository.DashboardRepositoryAPI
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

    fun getDashboardSensors(source: Single<Dashboard>): Observable<List<DashboardSensor>> {
        return source.map { transformSensors(it) }
            .toObservable()
    }

    fun fetchSensorsInInterval(): Observable<List<DashboardSensor>> =
        Observable.interval(0, 10, TimeUnit.SECONDS)
            .flatMap { getDashboardSensors(smartHomeAPI.getDashboard()) }
            .startWith( getDashboardSensors( dashboardRepository.getDashboard().toSingle()) )


    private fun transformSensors(dashboard: Dashboard): List<DashboardSensor> {
        val sensors = mutableListOf<DashboardSensor>()
        sensors.addAll(transformFromLights(dashboard.lights))
        sensors.addAll(transformFromTemperatureSensors(dashboard.temperatureSensors))
        sensors.addAll(transformFromSmokeSensors(dashboard.smokeSensors))
        sensors.addAll(transformFromWindowBlinds(dashboard.windowBlinds))
        sensors.addAll(transfromFromWindowSensors(dashboard.windowSensors))
        sensors.addAll(transformFromRFIDSensors(dashboard.RFIDSensors))
        sensors.addAll(transformFromHVACRooms(dashboard.HVACRooms))
        sensors.addAll(transfromFromHVACStatus(dashboard.HVACStatus))
        return sensors.toList()
    }

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
}