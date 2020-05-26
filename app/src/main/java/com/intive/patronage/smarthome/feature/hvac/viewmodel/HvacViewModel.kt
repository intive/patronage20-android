package com.intive.patronage.smarthome.feature.hvac.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.handleHttpResponseCode
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.hvac.model.api.HVACDetailsService
import com.intive.patronage.smarthome.feature.hvac.model.api.HVACRoomDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

const val type = "HVACRoom"

class HvacViewModel(
    private val dashboardService: DashboardService,
    var hvacViewEventListener: HVACViewEventListener,
    private val id: Int,
    private val hvacDetailsService: HVACDetailsService
) : ViewModel() {

    private var disposable: Disposable? = null
    private var updateStatusDisposable: Disposable? = null
    private var windowSensorIds = listOf<Int>()
    private var temperatureSensorId = 0
    var temperature: Float = 0F
    var hysteresis: Int = 0
    var coolingTemperature = 0
    var heatingTemperature = 0

    init {
        loadHvac()
    }

    private fun loadHvac() {
        disposable = dashboardService.getHVACById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.temperatureSensorId?.let { sensor -> getTemperatureFromSensor(sensor) }
                it?.hysteresis?.let { sensor -> getHysteresisFromSensor(sensor) }
                it?.coolingTemperature?.let { sensor -> getCoolingTemperature(sensor) }
                it?.heatingTemperature?.let { sensor -> getHeatingTemperature(sensor) }
                it?.windowSensorIds?.let { sensor -> getWindowSensorIds(sensor) }
            },
                {
                    Log.d("Error", "error loadhvac")
                })
    }

    private fun getTemperatureFromSensor(id: Int) {
        temperatureSensorId = id
        disposable = dashboardService.getTemperatureSensorById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    temperature = it.value.toFloat()
                    hvacViewEventListener.setTemperature(temperature)
                }
            },
                { Log.d("Error", "error") })

    }

    private fun getHysteresisFromSensor(value: Int) {
        hysteresis = value
        hvacViewEventListener.setHysteresis(hysteresis)
    }

    private fun getCoolingTemperature(value: Int) {
        coolingTemperature = value
        hvacViewEventListener.setCoolingTemperature(coolingTemperature)
    }

    private fun getHeatingTemperature(value: Int) {
        heatingTemperature = value
        hvacViewEventListener.setHeatingTemperature(heatingTemperature)
    }
    private fun getWindowSensorIds(value: List<Int>){
        windowSensorIds = value
    }

    fun saveSettings() {
        updateStatusDisposable = hvacDetailsService.changeHVACStatus(
            HVACRoomDTO(
                id, type, heatingTemperature, coolingTemperature,
                hysteresis, temperatureSensorId, windowSensorIds
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { handleHttpResponseCode(it.code(), R.string.apply_toast, R.string.update_value_toast_error, hvacViewEventListener::showToast) },
                { hvacViewEventListener.showToast(R.string.update_value_toast_error) }
            )
    }

    fun resetSetting() {
        loadHvac()
        hvacViewEventListener.resetSetting()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        updateStatusDisposable?.dispose()
    }
}