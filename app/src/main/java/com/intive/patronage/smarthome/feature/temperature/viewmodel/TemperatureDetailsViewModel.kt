package com.intive.patronage.smarthome.feature.temperature.viewmodel

import android.graphics.Point
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class TemperatureDetailsViewModel(private val dashboardService: DashboardService) : ObservableViewModel() {

    val data = MutableLiveData<List<Point>>()
    private var disposable: Disposable? = null
    val values = mutableListOf<Int>()

    init {
        disposable = dashboardService.getRoomDashboards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                if (list != null) {
                    val size: Int = list.lastIndex
                    list.forEach { dashboard ->
                        if (dashboard.id > size - 99) {
                            dashboard.temperatureSensors.forEach { sensor ->
                                values.add(sensor.value)
                            }
                        }
                    }
                    data.value = (1 until values.size + 1).map {
                        Point(it, values[it - 1])
                    }
                    disposable?.dispose()
                }
                else Log.d("Exception", "NULL")
            },{
                Log.d("Exception", "ERROR")
            })
    }
}