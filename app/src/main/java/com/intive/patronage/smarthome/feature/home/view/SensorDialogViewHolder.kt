package com.intive.patronage.smarthome.feature.home.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.SensorDialogType
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import kotlinx.android.synthetic.main.dialog_sensor_list_item.view.*

class SensorDialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindTo(
        sensor: DashboardSensor,
        onDialogSensorClickListener: (sensor: DashboardSensor) -> Unit
    ) {
        with(itemView) {
            itemView.setOnClickListener {
                onDialogSensorClickListener(sensor)
            }
            if (sensor.mapPosition != null) {
                dialogSensorImage.visibility = View.VISIBLE
            }
            val findSensor = SensorDialogType.values().find {
                it.type == sensor.type
            }

            for (sensorType in SensorDialogType.values()) {
                if (sensorType == findSensor) {
                    sensorType.setAttributes(sensor, itemView)
                }
            }
        }
    }
}