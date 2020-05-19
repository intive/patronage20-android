package com.intive.patronage.smarthome.feature.dashboard.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

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
            .doOnError{
                error.value = true
            }
            .retryWhen{ it.delay(5, TimeUnit.SECONDS) }
            .subscribe( {
                error.value = false
            }, { error.value = true })
    }

    override fun onCleared() {
        super.onCleared()
        sensorList?.dispose()
    }
}