package com.intive.patronage.smarthome.feature.dashboard.model.api.service

import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.feature.dashboard.model.*
import com.intive.patronage.smarthome.feature.dashboard.model.api.respository.DashboardRepositoryAPI
import com.intive.patronage.smarthome.feature.dashboard.model.api.room.repository.DashboardRoomRepositoryAPI
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.ReplaySubject
import java.util.concurrent.TimeUnit

const val intervalDelay = 10L

class DashboardService(
    private val smartHomeAPI: SmartHomeAPI,
    private val dashboardRepository: DashboardRepositoryAPI,
    private val dashboardRoomRepository: DashboardRoomRepositoryAPI
) {
    val dashboardReplaySubject = ReplaySubject.create<List<DashboardSensor>>()

    fun getDashboard(): Single<Dashboard> = dashboardRepository.getDashboard()
        .switchIfEmpty(getDashboardFromNetwork())

    fun getRoomDashboards() = dashboardRoomRepository.getAllDashboards()

    fun getDashboardFromNetwork(): Single<Dashboard> {
        return smartHomeAPI.getDashboard().doOnSuccess { dashboard ->
            dashboardRepository.setDashboard(dashboard)

            val aggregatedDashboard: Dashboard? = aggregateRequests(dashboard)
            if (aggregatedDashboard != null) dashboardRoomRepository.insertDashboard(
                aggregatedDashboard
            )
        }.doOnError {
            it.printStackTrace()
        }
    }

    fun fetchDashboardWithDelay(minWaitTime: Long, maxWaitTime: Long)
            : Observable<Pair<Dashboard, Long>> =
        getDashboardFromNetwork().toObservable()
            .retryWhen { Observable.timer(maxWaitTime, TimeUnit.SECONDS) }
            .zipWith(Observable.timer(minWaitTime, TimeUnit.SECONDS))

    private fun provideDashboardSensors(source: Single<Dashboard>): Observable<List<DashboardSensor>> {
        return source.map { transformSensors(it) }
            .toObservable()
            .doOnNext {
                dashboardReplaySubject.onNext(it)
            }
            .doOnError{

            }
    }

    fun fetchSensorsInInterval(): Observable<List<DashboardSensor>> =
        Observable.interval(intervalDelay, intervalDelay, TimeUnit.SECONDS)
            .flatMap { provideDashboardSensors(getDashboardFromNetwork()) }
            .startWith(provideDashboardSensors(getDashboard()))


    private fun transformSensors(dashboard: Dashboard): List<DashboardSensor> {
        val sensors = mutableListOf<DashboardSensor>()
        dashboard.lights?.let {
            sensors.addAll(transformFromLights(it))
        }
        dashboard.temperatureSensors?.let {
            sensors.addAll(transformFromTemperatureSensors(it))
        }
        dashboard.smokeSensors?.let {
            sensors.addAll(transformFromSmokeSensors(it))
        }
        dashboard.windowBlinds?.let {
            sensors.addAll(transformFromWindowBlinds(it))
        }
        dashboard.windowSensors?.let {
            sensors.addAll(transfromFromWindowSensors(it))
        }
        dashboard.RFIDSensors?.let {
            sensors.addAll(transformFromRFIDSensors(it))
        }
        dashboard.HVACRooms?.let {
            sensors.addAll(transformFromHVACRooms(it))
        }
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
            .filter { it.id == id }
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
            .filter { it.id == id }
            .firstOrError()
    }
}
