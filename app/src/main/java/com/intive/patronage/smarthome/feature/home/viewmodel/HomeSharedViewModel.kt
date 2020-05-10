package com.intive.patronage.smarthome.feature.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.common.coordinateToPercentX
import com.intive.patronage.smarthome.common.coordinateToPercentY
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.intive.patronage.smarthome.feature.home.model.api.service.HomeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeSharedViewModel(private val dashboardService: DashboardService, private val homeService: HomeService) : ViewModel() {

    val items = MutableLiveData<List<DashboardSensor>>()
    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null
    private var postSensorCall: Disposable? = null
    private var deleteSensorCall: Disposable? = null
    var responsePostCode = MutableLiveData<Int>()
    private var actualSensorX = 0f
    private var actualSensorY = 0f

    init {
        sensorList = dashboardService.dashboardReplaySubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                items.value = it
                error.value = false
            }, { error.value = true })
    }

    fun postSensor(id: Int, sensor: HomeSensor) {
        postSensorCall = homeService.postSensor(id, sensor)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responsePostCode.value = it.code()
            }, {
                it.printStackTrace()
            })
    }

    fun deleteSensor(id: Int) {
        deleteSensorCall = homeService.deleteSensor(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})
    }

    override fun onCleared() {
        super.onCleared()
        sensorList?.dispose()
        postSensorCall?.dispose()
        deleteSensorCall?.dispose()
    }

    fun setSensorPosition(x: Float, y: Float, imageWidth: Int, imageHeight: Int) {
        actualSensorX = coordinateToPercentX(x, imageWidth)
        actualSensorY = coordinateToPercentY(y, imageHeight)
    }

    fun getSensorXPosition(): Float{
        return actualSensorX
    }

    fun getSensorYPosition(): Float{
        return actualSensorY
    }
}