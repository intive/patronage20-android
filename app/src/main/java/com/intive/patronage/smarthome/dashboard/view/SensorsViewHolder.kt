package com.intive.patronage.smarthome.dashboard.view

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.logic.convertHSVtoRGB
import com.intive.patronage.smarthome.dashboard.model.DashboardSensor
import kotlinx.android.synthetic.main.sensor_list_item.view.*

class SensorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(sensor: DashboardSensor) {
        with(itemView) {
            when(sensor.type){
                "RGBLight" ->{
                    sensorName.text = "Light"
                    resources.getDrawable(R.drawable.light_bulb_inside).setTint(sensor.details.toInt())
                    sensorDetalis.visibility = View.GONE
                    sensorImage.setImageDrawable(resources.getDrawable(R.drawable.light))
                }
                "temperatureSensor" ->{
                    sensorName.text = "Temperature"
                    sensorDetalis.visibility = View.VISIBLE
                    sensorDetalis.text = sensor.details + "°C"
                    sensorImage.setImageDrawable(resources.getDrawable(R.drawable.thermometer))
                }
                "smokeSensor" ->{
                    sensorName.text = "Smoke sensor"
                    sensorDetalis.visibility = View.GONE
                    if(sensor.details == "true"){
                        sensorDetalis.visibility = View.VISIBLE
                        sensorDetalis.text = "Smoke detected"
                        resources.getDrawable(R.drawable.smoke_detector).setTint(Color.parseColor("#FF8C0000"))
                    }
                    sensorImage.setImageDrawable(resources.getDrawable(R.drawable.smoke_detector))
                }
                "windowBlind" ->{
                    sensorName.text = "Window blinds"
                    sensorDetalis.visibility = View.VISIBLE
                    sensorDetalis.text = sensor.details + "%"
                    sensorImage.setImageDrawable(resources.getDrawable(R.drawable.window_blinds))
                }
                "windowSensor" ->{
                    sensorName.text = "Window"
                    sensorDetalis.visibility = View.VISIBLE
                    sensorDetalis.text = sensor.details.toLowerCase().capitalize()
                    if(sensor.details == "OPEN"){
                        sensorImage.setImageDrawable(resources.getDrawable(R.drawable.window_open))
                    }
                    else {
                        sensorImage.setImageDrawable(resources.getDrawable(R.drawable.window_closed))
                    }
                }
                "RFIDSensor" ->{
                    sensorName.text = "RFID sensor"
                    sensorDetalis.visibility = View.GONE
                    sensorImage.setImageDrawable(resources.getDrawable(R.drawable.rfid_sensor))
                }
                "HVACRoom" ->{
                    sensorName.text = "HVAC"
                    sensorDetalis.visibility = View.GONE
                }
                else -> sensorName.text = sensor.type
            }
        }
    }
}