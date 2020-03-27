package com.intive.patronage.smarthome.feature.temperature.viewmodel

import android.graphics.Point
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import java.util.*

class TemperatureDetailsViewModel(private var dashboardService: DashboardService) : ObservableViewModel() {

    val data = MutableLiveData<List<Point>>()

    init {
        loadData()
    }

    fun loadData() {
        val random = Random()
        data.value = (1..10).map {
            Point(it, random.nextInt(21) + 10)
        }
    }
}