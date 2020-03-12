package com.intive.patronage.smarthome.dashboard.view

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.dashboard.model.SensorMock
import androidx.databinding.BindingAdapter

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(
    recyclerView: RecyclerView,
    items: MutableLiveData<List<SensorMock>>
) {
    if (recyclerView.adapter is SensorsListAdapter) {
        items.value?.let {
            (recyclerView.adapter as SensorsListAdapter).update(it)
        }
    }
}