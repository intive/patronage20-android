package com.intive.patronage.smarthome.feature.home.view

import androidx.recyclerview.widget.DiffUtil
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor

class SensorDialogDiffCallback : DiffUtil.ItemCallback<List<HomeSensor>>() {
    override fun areItemsTheSame(oldItem: List<HomeSensor>, newItem: List<HomeSensor>) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: List<HomeSensor>,
        newItem: List<HomeSensor>
    ) = oldItem == newItem
}