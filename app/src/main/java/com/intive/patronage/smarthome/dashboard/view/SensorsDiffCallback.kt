package com.intive.patronage.smarthome.dashboard.view

import androidx.recyclerview.widget.DiffUtil
import com.intive.patronage.smarthome.dashboard.model.SensorMock

class SensorsDiffCallback : DiffUtil.ItemCallback<SensorMock>() {
    override fun areItemsTheSame(oldItem: SensorMock, newItem: SensorMock) = oldItem == newItem
    override fun areContentsTheSame(oldItem: SensorMock, newItem: SensorMock) = oldItem == newItem
}