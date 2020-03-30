package com.intive.patronage.smarthome.feature.home.view

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.BindingAdapter

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(
    recyclerView: RecyclerView,
    items: List<DialogSensorMock>
) {
    if (recyclerView.adapter is SensorDialogListAdapter) {
        items.let {
            (recyclerView.adapter as SensorDialogListAdapter).update(it)
        }
    }
}