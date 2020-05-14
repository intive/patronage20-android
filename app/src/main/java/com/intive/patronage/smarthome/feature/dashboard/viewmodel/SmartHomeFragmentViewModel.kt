package com.intive.patronage.smarthome.feature.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SmartHomeFragmentViewModel(val dashboardService: DashboardService)  : ViewModel() {

    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null

    init {
        fetchSensors()
    }

    fun fetchSensors() {
        sensorList = dashboardService.fetchSensorsInInterval()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe({
                error.value = false
            }, { error.value = true })
    }

    override fun onCleared() {
        super.onCleared()
        sensorList?.dispose()
    }
}