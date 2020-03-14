package com.intive.patronage.smarthome.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.dashboard.model.Dashboard
import com.intive.patronage.smarthome.dashboard.model.SensorMock
import com.intive.patronage.smarthome.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DashboardViewModel(dashboardService: DashboardService) : ViewModel() {

    val items = MutableLiveData<Dashboard>()
    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null

//    init {
//        val s1 = SensorMock(1, "Temperature")
//        val s2 = SensorMock(2, "Windows")
//        val s3 = SensorMock(3, "Light")
//        val s4 = SensorMock(4, "HVAC")
//        val s5 = SensorMock(5, "Windows Blinds")
//        val s6 = SensorMock(6, "Smoke Sensor")
//        val s7 = SensorMock(7, "RFID Sensor")
//        val s8 = SensorMock(8, "Light2")
//        val s9 = SensorMock(9, "Light3")
//        val s10 = SensorMock(10, "Light4")
//
//        val sensors = listOf<SensorMock>(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10)
//        items.value = sensors
//    }

    // tbd when API delivered(retrofit)
    init {
        sensorList = dashboardService.getDashboard()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                items.value = it
            }, {
                error.value = true
            })
    }

    override fun onCleared() {
        super.onCleared()
        sensorList?.dispose()
    }

}