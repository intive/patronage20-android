package com.intive.patronage.smarthome.dashboard.view

import androidx.recyclerview.widget.DiffUtil
import com.intive.patronage.smarthome.dashboard.model.Dashboard

class SensorsDiffCallback : DiffUtil.ItemCallback<Dashboard>() {
    override fun areItemsTheSame(oldItem: Dashboard, newItem: Dashboard) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Dashboard, newItem: Dashboard) = oldItem == newItem
}