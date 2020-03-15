package com.intive.patronage.smarthome.dashboard.view

import androidx.recyclerview.widget.DiffUtil
import com.intive.patronage.smarthome.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.dashboard.model.SensorMock

class SensorsDiffCallback : DiffUtil.ItemCallback<DashboardSensor>() {
    override fun areItemsTheSame(oldItem: DashboardSensor, newItem: DashboardSensor) = oldItem == newItem
    override fun areContentsTheSame(oldItem: DashboardSensor, newItem: DashboardSensor) = oldItem == newItem
}