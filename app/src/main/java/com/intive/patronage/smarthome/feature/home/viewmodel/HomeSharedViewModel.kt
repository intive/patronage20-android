package com.intive.patronage.smarthome.feature.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.SensorType
import com.intive.patronage.smarthome.common.ToastListener
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.intive.patronage.smarthome.feature.home.model.api.service.HomeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeSharedViewModel(
    private val dashboardService: DashboardService,
    private val homeService: HomeService
) : ViewModel() {

    val items = MutableLiveData<List<DashboardSensor>>()
    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null
    private var postSensorCall: Disposable? = null
    private var deleteSensorCall: Disposable? = null
    lateinit var toastListener: ToastListener
    var isDragOnMap: Boolean = false

    init {
        sensorList = dashboardService.dashboardBehaviorSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (!isDragOnMap) {
                    items.value = it.filter { it.type != SensorType.HVAC_ROOM.type }
                }
                error.value = false
            }, { error.value = true })
    }

    fun postSensor(id: Int, sensor: HomeSensor) {
        postSensorCall = homeService.postSensor(id, sensor)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toastListener.showToast(R.string.sensor_add_success)
            }, {
                toastListener.showToast(R.string.sensor_add_failure)
                it.printStackTrace()
            })
    }

    fun deleteSensor(id: Int) {
        deleteSensorCall = homeService.deleteSensor(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toastListener.showToast(R.string.sensor_removed)
            }, {
                toastListener.showToast(R.string.sensor_add_failure)
            })
    }

    override fun onCleared() {
        super.onCleared()
        sensorList?.dispose()
        postSensorCall?.dispose()
        deleteSensorCall?.dispose()
    }
}