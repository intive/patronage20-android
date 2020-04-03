package com.intive.patronage.smarthome.feature.temperature.viewmodel

import android.graphics.Point
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TemperatureDetailsViewModel(private val dashboardService: DashboardService) : ObservableViewModel() {

    val data = MutableLiveData<List<Point>>()
    private var disposable: Disposable? = null
    val values = mutableListOf<Int>()

    init {
        loadData(60)
    }

    fun loadData(requestCount: Int) {
        disposable = dashboardService.getRoomDashboards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                if (list != null) {
                    val size: Int = list.lastIndex
                    var counter = requestCount / 100
                    list.forEach { dashboard ->
                        if (dashboard.id > (size - requestCount - 1)) {
                            if (counter == (requestCount / 100)) {
                                dashboard.temperatureSensors.forEach { sensor ->
                                    values.add(sensor.value)
                                }
                                counter = 0
                            }
                            counter++
                        }
                    }
                    data.value = (1 until values.size + 1).map {
                        Point(it, values[it - 1])
                    }
                    values.clear()
                    disposable?.dispose()
                }
                else Log.d("Exception", "NULL")
            },{
                Log.d("Exception", "ERROR")
            })
    }
}