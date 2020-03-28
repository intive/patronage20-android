package com.intive.patronage.smarthome.feature.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class DashboardViewModel(dashboardService: DashboardService) : ViewModel() {

    val items = MutableLiveData<List<DashboardSensor>>()
    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null

    init {
        sensorList = dashboardService.fetchSensorsInInterval()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                items.value = it
            }, { error.value = true })
    }

    override fun onCleared() {
        super.onCleared()
        sensorList?.dispose()
    }

}