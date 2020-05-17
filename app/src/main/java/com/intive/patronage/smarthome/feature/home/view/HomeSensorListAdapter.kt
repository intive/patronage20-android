package com.intive.patronage.smarthome.feature.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.replace
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor

class HomeSensorListAdapter(private val onHomeSensorClickListener: (sensor: DashboardSensor, x: Float, y: Float, draggedView: View) -> Unit) :
    ListAdapter<List<DashboardSensor>, HomeSensorViewHolder>(HomeSensorDiffCallback()) {

    private val sensorsList = mutableListOf<DashboardSensor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSensorViewHolder =
        HomeSensorViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.home_sensor_list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeSensorViewHolder, position: Int) {
        holder.bindTo(sensorsList[position], onHomeSensorClickListener)
    }

    override fun getItemCount() = sensorsList.size

    fun update(sensorsList: List<DashboardSensor>) {
        this.sensorsList.replace(sensorsList.filter { it.mapPosition == null })
        notifyDataSetChanged()
    }
}