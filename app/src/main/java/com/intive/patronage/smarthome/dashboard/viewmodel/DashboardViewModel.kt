package com.intive.patronage.smarthome.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.dashboard.model.SensorMock
import io.reactivex.disposables.Disposable

class DashboardViewModel : ViewModel() {

    val items = MutableLiveData<List<SensorMock>>()
    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null

    

//    override fun onCleared() {
//        super.onCleared()
//        itemsCall?.dispose()
//    }

}