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

    init {
        loadData()

        disposable = dashboardService.getRoomDashboards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                if (list != null) {
                    list.forEach { dashboard ->
                        dashboard.temperatureSensors.forEach {
                            Log.d("Temperature:", it.value.toString())
                        }
                    }
                    disposable?.dispose()
                }
                else Log.d("Exception", "NULL")
            },{
                Log.d("Exception", "ERROR")
            })
    }

    fun loadData() {
        val random = Random()
        data.value = (1..10).map {
            Point(it, random.nextInt(21) + 10)
        }
    }
}