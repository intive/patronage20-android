package com.intive.patronage.smarthome.feature.temperature.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.temperature.utilities.GraphPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TemperatureDetailsViewModel(private val dashboardService: DashboardService, private val id: Int) : ObservableViewModel() {

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

    private fun calculateMovingAverage(dashboard: Dashboard, number: Int) {
        counter[number]++

        dashboard.temperatureSensors.forEach {
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

    private fun setData() {
        data.value = (1 until values.size + 1).map {
            GraphPoint(it, values[it - 1])
        }

        values.clear()
        disposable?.dispose()
    }

    fun subscribe(requestAmount: Int, interval: Int) {
        this.requestAmount = requestAmount
        this.interval = interval
        sumOfTemperatureValues = Array(interval){0f}
        counter = Array(interval){0}

        disposable = dashboardService.getRoomDashboards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                if (list != null) {
                    val dashboardsAmount = list.lastIndex + 1

                    if (dashboardsAmount > (requestAmount + (interval - 1))) {
                        list.forEach { dashboard ->
                            when (requestAmount) {
                                60 -> loadLast1h(dashboard, dashboardsAmount)
                                180 -> loadLast3h(dashboard, dashboardsAmount)
                                360 -> loadLast6h(dashboard, dashboardsAmount)
                                //720 -> loadLast12h(dashboard, dashboardsAmount)
                                //1440 -> loadLast24h(dashboard, dashboardsAmount)
                            }
                        }

                        setData()
                    }
                } else {
                    Log.d("Exception", "NULL")
                }
            },{
                Log.d("Exception", "ERROR")
            }
        )
    }

    private fun loadLast1h(dashboard: Dashboard, dashboardsAmount: Int) {
        if (dashboard.id > dashboardsAmount - requestAmount - 2) {
            calculateMovingAverage(dashboard, 0)

            if (dashboard.id > dashboardsAmount - requestAmount - 1) {
                calculateMovingAverage(dashboard, 1)

                if (dashboard.id > dashboardsAmount - requestAmount) {
                    calculateMovingAverage(dashboard, 2)
                }
            }
        }
    }

    private fun loadLast3h(dashboard: Dashboard, dashboardsAmount: Int) {
        if (dashboard.id > dashboardsAmount - requestAmount - 5) {
            calculateMovingAverage(dashboard, 0)

            if (dashboard.id > dashboardsAmount - requestAmount - 3) {
                calculateMovingAverage(dashboard, 1)

                if (dashboard.id > dashboardsAmount - requestAmount - 1) {
                    calculateMovingAverage(dashboard, 2)

                    if (dashboard.id > dashboardsAmount - requestAmount + 1) {
                        calculateMovingAverage(dashboard, 3)

                        if (dashboard.id > dashboardsAmount - requestAmount + 3) {
                            calculateMovingAverage(dashboard, 4)

                            if (dashboard.id > dashboardsAmount - requestAmount + 5) {
                                calculateMovingAverage(dashboard, 5)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadLast6h(dashboard: Dashboard, dashboardsAmount: Int) {
        if (dashboard.id > dashboardsAmount - requestAmount - 11) {
            calculateMovingAverage(dashboard, 0)

            if (dashboard.id > dashboardsAmount - requestAmount - 7) {
                calculateMovingAverage(dashboard, 1)

                if (dashboard.id > dashboardsAmount - requestAmount - 3) {
                    calculateMovingAverage(dashboard, 2)

                    if (dashboard.id > dashboardsAmount - requestAmount + 1) {
                        calculateMovingAverage(dashboard, 3)

                        if (dashboard.id > dashboardsAmount - requestAmount + 5) {
                            calculateMovingAverage(dashboard, 4)

                            if (dashboard.id > dashboardsAmount - requestAmount + 9) {
                                calculateMovingAverage(dashboard, 5)

                                if (dashboard.id > dashboardsAmount - requestAmount + 13) {
                                    calculateMovingAverage(dashboard, 6)

                                    if (dashboard.id > dashboardsAmount - requestAmount + 17) {
                                        calculateMovingAverage(dashboard, 7)

                                        if (dashboard.id > dashboardsAmount - requestAmount + 21) {
                                            calculateMovingAverage(dashboard, 8)

                                            if (dashboard.id > dashboardsAmount - requestAmount + 25) {
                                                calculateMovingAverage(dashboard, 9)

                                                if (dashboard.id > dashboardsAmount - requestAmount + 29) {
                                                    calculateMovingAverage(dashboard, 10)

                                                    if (dashboard.id > dashboardsAmount - requestAmount + 34) {
                                                        calculateMovingAverage(dashboard, 11)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}