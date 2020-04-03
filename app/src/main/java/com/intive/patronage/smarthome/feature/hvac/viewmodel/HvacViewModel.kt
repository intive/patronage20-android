package com.intive.patronage.smarthome.feature.hvac.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HvacViewModel(
    private val dashboardService: DashboardService,
    var hvacViewEventListener: HVACViewEventListener,
    private val id: Int
) : ViewModel() {


    private var disposable: Disposable? = null
    var temperature: Float = 0F
    var hysteresis: Int = 0
    var coolingTemperature = 220
    var heatingTemperature = 120

    var temperatureFromView = temperature

    init {
        loadHvac()
    }

    fun loadHvac() {
        disposable = dashboardService.getHVACById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.temperatureSensorId?.let { it1 -> getTemperatureFromSensor(it1) }
                it?.hysteresis?.let { it1 -> getHysteresisFromSensor(it1) }
                it?.coolingTemperature?.let { it1 -> getCoolingTemperature(220) }
                it?.heatingTemperature?.let { it1 -> getHeatingTemperature(120) }
            },
                {
                    hvacViewEventListener.connectionError(true)
                    Log.d("Error", "error loadhvac")
                })
    }

    private fun getTemperatureFromSensor(id: Int) {
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

    fun saveSettings() {
        hvacViewEventListener.saveSetting()
    }

    fun resetSetting() {
        loadHvac()
        hvacViewEventListener.resetSetting()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}