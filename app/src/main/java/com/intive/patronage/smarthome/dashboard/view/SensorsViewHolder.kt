package com.intive.patronage.smarthome.dashboard.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.model.DashboardSensor
import kotlinx.android.synthetic.main.sensor_list_item.view.*

class SensorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(sensor: DashboardSensor, onDashboardClickListener:(sensor: DashboardSensor)-> Unit) {
        with(itemView) {
            itemView.setOnClickListener {
                onDashboardClickListener(sensor)
            }
            when (sensor.type) {
                "RGBLight" -> {
                    sensorName.text = context.getString(R.string.light_sensor_name)
                    resources.getDrawable(R.drawable.light_bulb_inside)
                        .setTint(sensor.details.toInt())
                    sensorDetalis.visibility = View.GONE
                    sensorImage.setImageDrawable(resources.getDrawable(R.drawable.light))
                }
                "temperatureSensor" -> {
                    sensorName.text = context.getString(R.string.temperature_sensor_name)
                    sensorDetalis.visibility = View.VISIBLE
                    sensorDetalis.text =
                        context.getString(R.string.temperature_details, sensor.details)
                    sensorImage.setImageDrawable(resources.getDrawable(R.drawable.thermometer))
                }
                "smokeSensor" -> {
                    sensorName.text = context.getString(R.string.smoke_sensor_name)
                    sensorDetalis.visibility = View.GONE
                    if (sensor.details == "true") {
                        sensorDetalis.visibility = View.VISIBLE
                        sensorDetalis.text = context.getString(R.string.smoke_detected)
                        resources.getDrawable(R.drawable.smoke_detector)
                            .setTint(resources.getColor(R.color.alert))
                    }
                    sensorImage.setImageDrawable(resources.getDrawable(R.drawable.smoke_detector))
                }
                "windowBlind" -> {
                    sensorName.text = context.getString(R.string.blinds_sensor_name)
                    sensorDetalis.visibility = View.VISIBLE
                    sensorDetalis.text =
                        context.getString(R.string.window_blinds_details, sensor.details)
                    sensorImage.setImageDrawable(resources.getDrawable(R.drawable.window_blinds))
                }
                "windowSensor" -> {
                    sensorName.text = context.getString(R.string.window_sensor_name)
                    sensorDetalis.visibility = View.VISIBLE
                    sensorDetalis.text = sensor.details.toLowerCase().capitalize()
                    if (sensor.details == "OPEN") {
                        sensorImage.setImageDrawable(resources.getDrawable(R.drawable.window_open))
                    } else {
                        sensorImage.setImageDrawable(resources.getDrawable(R.drawable.window_closed))
                    }
                }
                "RFIDSensor" -> {
                    sensorName.text = context.getString(R.string.RFID_sensor_name)
                    sensorDetalis.visibility = View.GONE
                    sensorImage.setImageDrawable(resources.getDrawable(R.drawable.rfid_sensor))
                }
                "HVACRoom" -> {
                    sensorName.text = context.getString(R.string.HVAC_sensor_name)
                    sensorDetalis.visibility = View.GONE
                }
                else -> sensorName.text = sensor.type
            }
        }
    }
}