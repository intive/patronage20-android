package com.intive.patronage.smarthome.feature.temperature.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.TEMPERATURE_DIVISOR
import com.intive.patronage.smarthome.feature.temperature.utils.GraphPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TemperatureDetailsViewModel(
    private val dashboardService: DashboardService,
    private val id: Int
) : ObservableViewModel() {

    val data = MutableLiveData<List<GraphPoint>>()
    private var disposable: Disposable? = null
    val values = mutableListOf<Float>()

    private var requestAmount = 60
    private var interval = 3
    private var sumOfTemperatureValues = Array(interval){0f}
    private var counter = Array(interval){0}

    init {
        subscribe(60, 3)
    }

    private fun setData() {
        data.value = (1 until values.size + 1).map {
            GraphPoint(it, (values[it - 1]) / TEMPERATURE_DIVISOR)
        }

        values.clear()
        disposable?.dispose()
    }

    fun subscribe(requestAmount: Int, interval: Int) {
        this.requestAmount = requestAmount
        this.interval = interval
        sumOfTemperatureValues = Array(interval){0f}
        counter = Array(interval){0}

        disposable?.dispose()
        disposable = dashboardService.getRoomDashboards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                if (list != null) {
                    val dashboardsAmount = list.lastIndex + 1

                    list.forEach { dashboard ->
                        if(requestAmount < 90) {
                            loadLastHours(dashboard, dashboardsAmount, 1)
                        } else {
                            loadLastHours(dashboard, dashboardsAmount, requestAmount / 90)
                        }
                    }

                    setData()
                } else {
                    Log.d("Exception", "NULL")
                }
            },{
                Log.d("Exception", "ERROR")
            })
    }

    private fun loadLastHours(dashboard: Dashboard, dashboardsAmount: Int, counterBreak: Int) {
        if (dashboard.id > dashboardsAmount - requestAmount - (interval - 1)) {
            calculateMovingAverage(dashboard, 0)

            if (dashboard.id > dashboardsAmount - requestAmount - (interval - 1 - counterBreak)) {
                calculateMovingAverage(dashboard, 1)

                if (dashboard.id > dashboardsAmount - requestAmount - (interval - 1 - counterBreak - counterBreak)) {
                    calculateMovingAverage(dashboard, 2)
                }
            }
        }
    }

    private fun calculateMovingAverage(dashboard: Dashboard, number: Int) {
        counter[number]++

        dashboard.temperatureSensors?.forEach {
            if (it.id == this.id) {
                sumOfTemperatureValues[number] += it.value.toFloat()
            }
        }

        if (counter[number] == interval) {
            val average: Float = sumOfTemperatureValues[number] / interval
            values.add(average)
            counter[number] = 0
            sumOfTemperatureValues[number] = 0f
        }
    }
}