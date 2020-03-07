package com.intive.patronage.smarthome.dashboard.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.model.SensorMock

class SensorsListAdapter : ListAdapter<SensorMock, SensorsViewHolder>(SensorsDiffCallback()) {

    private val sensorsList = mutableListOf<SensorMock>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorsViewHolder =
        SensorsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.sensor_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: SensorsViewHolder, position: Int) {
        holder.bindTo(sensorsList[position])
    }

    override fun getItemCount() = sensorsList.size

    fun update(sensorsList: List<SensorMock>) {
        this.sensorsList.clear()
        this.sensorsList.addAll(sensorsList)
        notifyDataSetChanged()
    }
}