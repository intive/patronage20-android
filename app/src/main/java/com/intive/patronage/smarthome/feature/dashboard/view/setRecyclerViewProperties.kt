package com.intive.patronage.smarthome.feature.dashboard.view

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.home.view.HomeSensorListAdapter
import com.intive.patronage.smarthome.feature.home.view.SensorDialogListAdapter

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(
    recyclerView: RecyclerView,
    items: MutableLiveData<List<DashboardSensor>>
) {
    if (recyclerView.adapter is SensorDialogListAdapter) {
        items.value?.let {
            (recyclerView.adapter as SensorDialogListAdapter).update(it)
        }
    }
    if (recyclerView.adapter is SensorsListAdapter) {
        items.value?.let {
            (recyclerView.adapter as SensorsListAdapter).update(it)
        }
    }
    if (recyclerView.adapter is HomeSensorListAdapter) {
        items.value?.let {
            (recyclerView.adapter as HomeSensorListAdapter).update(it)
        }
    }
}