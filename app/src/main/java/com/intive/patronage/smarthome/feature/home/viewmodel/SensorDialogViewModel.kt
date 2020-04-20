package com.intive.patronage.smarthome.feature.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.intive.patronage.smarthome.feature.home.model.api.service.HomeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SensorDialogViewModel(homeService: HomeService) : ViewModel() {

    val items = MutableLiveData<List<HomeSensor>>()
    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null

    init {
        sensorList = homeService.fetchSensorsInInterval()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe({
                items.value = it
                error.value = false
            }, { error.value = true })
    }

    override fun onCleared() {
        super.onCleared()
        sensorList?.dispose()
    }
}