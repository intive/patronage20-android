package com.intive.patronage.smarthome.dashboard.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.replace

class SensorsListAdapter : ListAdapter<DashboardSensor, SensorsViewHolder>(SensorsDiffCallback()) {

    private val sensorsList = mutableListOf<DashboardSensor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorsViewHolder =
        SensorsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.sensor_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: SensorsViewHolder, position: Int) {
        holder.bindTo(sensorsList[position])
    }

    override fun getItemCount() = sensorsList.size

    fun update(sensorsList: List<DashboardSensor>) {
        this.sensorsList.replace(sensorsList)
        notifyDataSetChanged()
    }
}