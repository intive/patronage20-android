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
// te dane ustawiają się wcześniej niż tworzy się widok więc jest git
        // dodałem żeby działało chwilowo
        val random = Random()
        data.value = (1..11).map {
            Point(it, random.nextInt(21) + 10)
        }
        // =========


        disposable = dashboardService.getRoomDashboards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                if (list != null) {
                    val size: Int = list.lastIndex
                    list.forEach { dashboard ->
                        if (dashboard.id > size - 10) {
                            dashboard.temperatureSensors.forEach { sensor ->
                                values.add(sensor.value)
                            }
                        }
                    }
// te dane ustawiają się później niż tworzy się widok więc leci crash
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