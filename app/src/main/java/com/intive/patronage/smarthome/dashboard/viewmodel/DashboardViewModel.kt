package com.intive.patronage.smarthome.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.dashboard.model.SensorMock
import io.reactivex.disposables.Disposable

class DashboardViewModel : ViewModel() {

    val items = MutableLiveData<List<SensorMock>>()
    val error = MutableLiveData<Boolean>().apply { value = false }
    private var sensorList: Disposable? = null


    // tbd when API delivered(retrofit)
//    init {
//        itemsCall = matchResultService.fetchMatchResults()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                items.value = it
//            }, {
//                error.value = true
//            })
//    }

//    override fun onCleared() {
//        super.onCleared()
//        itemsCall?.dispose()
//    }

}