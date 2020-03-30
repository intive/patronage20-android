package com.intive.patronage.smarthome.feature.home.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.SensorDialogType
import kotlinx.android.synthetic.main.dialog_sensor_list_item.view.*

class SensorDialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindTo(
        sensor: DialogSensorMock,
        onDialogSensorClickListener: (sensor: DialogSensorMock) -> Unit
    ) {
        with(itemView) {
            itemView.setOnClickListener {
                onDialogSensorClickListener(sensor)
            }
            if (sensor.added) {
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