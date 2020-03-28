package com.intive.patronage.smarthome.feature.home.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
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
            when (sensor.type) {
                "RGBLight" -> {
                    dialogSensorName.text = context.getString(R.string.light_sensor_name)
                }
                "temperatureSensor" -> {
                    dialogSensorName.text = context.getString(R.string.temperature_sensor_name)
                }
                "smokeSensor" -> {
                    dialogSensorName.text = context.getString(R.string.smoke_sensor_name)
                }
                "windowBlind" -> {
                    dialogSensorName.text = context.getString(R.string.blinds_sensor_name)
                }
                "windowSensor" -> {
                    dialogSensorName.text = context.getString(R.string.window_sensor_name)
                }
                "RFIDSensor" -> {
                    dialogSensorName.text = context.getString(R.string.RFID_sensor_name)
                }
                "HVACRoom" -> {
                    dialogSensorName.text = context.getString(R.string.HVAC_sensor_name)
                }
                else -> dialogSensorName.text = sensor.type
            }
        }
    }
}