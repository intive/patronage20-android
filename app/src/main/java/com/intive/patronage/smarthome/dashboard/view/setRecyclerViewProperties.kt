package com.intive.patronage.smarthome.dashboard.view

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.dashboard.model.Dashboard

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(
    recyclerView: RecyclerView,
    items: MutableLiveData<Dashboard>
) {
    if (recyclerView.adapter is SensorsListAdapter) {
        items.value?.let {
            (recyclerView.adapter as SensorsListAdapter).update(it)
        }
    }
}