package com.intive.patronage.smarthome.feature.dashboard.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.common.replace

class SensorsListAdapter(private val onDashboardClickListener: (sensor: DashboardSensor) -> Unit) :
    ListAdapter<List<DashboardSensor>, SensorsViewHolder>(SensorsDiffCallback()) {

    private val sensorsList = mutableListOf<DashboardSensor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorsViewHolder =
        SensorsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.sensor_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: SensorsViewHolder, position: Int) {
        holder.bindTo(sensorsList[position], onDashboardClickListener)
    }

    override fun getItemCount() = sensorsList.size

    fun update(sensorsList: List<DashboardSensor>) {
        val isDataChanged = this.sensorsList.isEmpty() && sensorsList.isNotEmpty()
        this.sensorsList.replace(sensorsList)
        if (isDataChanged) notifyDataSetChanged()
        else notifyItemRangeChanged(0, sensorsList.size)
    }

}