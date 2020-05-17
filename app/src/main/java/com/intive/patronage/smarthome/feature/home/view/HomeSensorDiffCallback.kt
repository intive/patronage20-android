package com.intive.patronage.smarthome.feature.home.view

import androidx.recyclerview.widget.DiffUtil
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor

class HomeSensorDiffCallback : DiffUtil.ItemCallback<List<DashboardSensor>>() {
    override fun areItemsTheSame(oldItem: List<DashboardSensor>, newItem: List<DashboardSensor>) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: List<DashboardSensor>,
        newItem: List<DashboardSensor>
    ) = oldItem == newItem
}