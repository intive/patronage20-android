package com.intive.patronage.smarthome.feature.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.home.model.HomeRepository
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.intive.patronage.smarthome.feature.home.model.api.service.HomeService
import com.intive.patronage.smarthome.feature.home.view.HomeFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.context.KoinContextHandler.get
import org.koin.java.KoinJavaComponent.inject

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