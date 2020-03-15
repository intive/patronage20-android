package com.intive.patronage.smarthome.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.dashboard.model.api.service.DashboardService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class DashboardViewModel(dashboardService: DashboardService) : ViewModel() {

    val items = MutableLiveData<List<DashboardSensor>>()
    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null

    init {
        sensorList = dashboardService.getDashboard()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                items.value = dashboardService.getDashboardSensors()
            }, {
                error.value = true
            })
        updateSensors(dashboardService)
    }

    fun updateSensors(dashboardService: DashboardService): Observable<DashboardSensor> = Observable.interval(5, 10, TimeUnit.SECONDS)
        .flatMap {
            dashboardService.getDashboardSensors().toObservable()
        }

    override fun onCleared() {
        super.onCleared()
        sensorList?.dispose()
    }

}