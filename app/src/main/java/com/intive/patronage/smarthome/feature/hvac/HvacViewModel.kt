package com.intive.patronage.smarthome.feature.hvac

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
    var hysteresis: Float = 0F
    var temperatureSensorId = 0
    var temperatureFromView = temperature

    init {
        loadHvac()
    }

    private fun loadTemperatureSensor() {
        disposable = dashboardService.getTemperatureSensorById(temperatureSensorId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    temperature = it.value.toFloat()
                    hvacViewEventListener.setTemperature(temperature)
                } else {
                    Log.d("testowanie model", "ERROR")
                }
            },
                { Log.d("Error", "error") })
    }

    private fun loadHvac() {

        disposable = dashboardService.getHVACById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    hvacViewEventListener.setTemperature(temperature)
                    hysteresis = it.hysteresis.toFloat()
                    temperatureSensorId = it.temperatureSensorId
                    loadTemperatureSensor()
                    hvacViewEventListener.setHysteresis(hysteresis)
                } else {
                    Log.d("testowanie model", "ERROR")
                }
            },
                { Log.d("Error", "error") })
    }


    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}