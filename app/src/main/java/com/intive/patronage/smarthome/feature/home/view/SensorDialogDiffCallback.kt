package com.intive.patronage.smarthome.feature.home.view

import androidx.recyclerview.widget.DiffUtil

class SensorDialogDiffCallback : DiffUtil.ItemCallback<List<DialogSensorMock>>() {
    override fun areItemsTheSame(oldItem: List<DialogSensorMock>, newItem: List<DialogSensorMock>) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: List<DialogSensorMock>,
        newItem: List<DialogSensorMock>
    ) = oldItem == newItem
}