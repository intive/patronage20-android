package com.intive.patronage.smarthome.feature.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.replace
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor

class SensorDialogListAdapter(private val onDialogSensorClickListener: (sensor: HomeSensor) -> Unit) :
    ListAdapter<List<HomeSensor>, SensorDialogViewHolder>(SensorDialogDiffCallback()) {

    private val sensorsList = mutableListOf<HomeSensor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorDialogViewHolder =
        SensorDialogViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.dialog_sensor_list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SensorDialogViewHolder, position: Int) {
        holder.bindTo(sensorsList[position], onDialogSensorClickListener)
    }

    override fun getItemCount() = sensorsList.size

    fun update(sensorsList: List<HomeSensor>) {
        this.sensorsList.replace(sensorsList)
        notifyDataSetChanged()
    }

}