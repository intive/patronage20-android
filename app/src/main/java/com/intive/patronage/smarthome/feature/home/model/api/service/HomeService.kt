package com.intive.patronage.smarthome.feature.home.model.api.service

import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.feature.dashboard.model.*
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

const val intervalDelay = 10L

class HomeService(private val dashboardService: DashboardService,
                  private val smartHomeAPI: SmartHomeAPI) {

    private fun provideHomeSensors(source: Single<Dashboard>): Observable<List<HomeSensor>> {
        return source.map { transformSensors(it) }
            .toObservable()
    }

    fun fetchSensorsInInterval(): Observable<List<HomeSensor>> =
        Observable.interval(intervalDelay, intervalDelay, TimeUnit.SECONDS)
            .flatMap { provideHomeSensors(dashboardService.getDashboardFromNetwork()) }
            .startWith(provideHomeSensors(dashboardService.getDashboard()))

    fun postSensor(id: Int, body: HomeSensor) = smartHomeAPI.addSensor(id, body)

    fun deleteSensor(id: Int) = smartHomeAPI.deleteSensor(id)


    private fun transformSensors(dashboard: Dashboard): List<HomeSensor> {
        val sensors = mutableListOf<HomeSensor>()
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
//            sensors.addAll(transformFromHVACRooms(it))
        }
        // TODO: verify when API ready
        // sensors.addAll(transfromFromHVACStatus(dashboard.HVACStatus))
        return sensors.toList()

    }
}
