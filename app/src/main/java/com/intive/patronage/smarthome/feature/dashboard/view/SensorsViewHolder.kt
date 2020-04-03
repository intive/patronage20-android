package com.intive.patronage.smarthome.feature.dashboard.view

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.SensorType
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor

class SensorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(sensor: DashboardSensor, onDashboardClickListener:(sensor: DashboardSensor)-> Unit) {
        itemView.setOnClickListener {
            onDashboardClickListener(sensor)
        }
        val findSensor = SensorType.values().find {
            it.type == sensor.type
        }

        for (sensorType in SensorType.values()) {
            if (sensorType == findSensor) {
                sensorType.setAttributes(sensor, itemView)
            }
        }
    }
}