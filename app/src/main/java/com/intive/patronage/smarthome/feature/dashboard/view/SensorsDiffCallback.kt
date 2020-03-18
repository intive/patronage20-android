package com.intive.patronage.smarthome.feature.dashboard.view

import androidx.recyclerview.widget.DiffUtil
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor

class SensorsDiffCallback : DiffUtil.ItemCallback<List<DashboardSensor>>() {
    override fun areItemsTheSame(oldItem: List<DashboardSensor>, newItem: List<DashboardSensor>) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: List<DashboardSensor>,
        newItem: List<DashboardSensor>
    ) = oldItem == newItem
}