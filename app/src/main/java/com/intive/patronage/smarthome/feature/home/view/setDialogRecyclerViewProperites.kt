package com.intive.patronage.smarthome.feature.home.view

import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(
    recyclerView: RecyclerView,
    items: MutableLiveData<List<HomeSensor>>
) {
    if (recyclerView.adapter is SensorDialogListAdapter) {
        items.value?.let {
            (recyclerView.adapter as SensorDialogListAdapter).update(it)
        }
    }
}