package com.intive.patronage.smarthome

import android.graphics.Color
import android.graphics.ColorFilter
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import kotlinx.android.synthetic.main.sensor_list_item.view.*

enum class SensorType(val type: String) {

    RGB_LIGHT("RGBLight") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.GONE, R.string.light_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.lightSensor, null))
            view.resources.getDrawable(R.drawable.light_bulb_inside, null).setTint(sensor.details.toInt())
            view.sensorImage.setImageDrawable(view.resources.getDrawable(R.drawable.light, null).mutate())
        }
    },
    TEMPERATURE_SENSOR("temperatureSensor") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.VISIBLE, R.string.temperature_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.temperatureSensor, null))
            view.sensorDetalis.text = view.context.getString(R.string.temperature_details, sensor.details)
            view.sensorImage.setImageDrawable(view.resources.getDrawable(R.drawable.thermometer, null))
        }
    },
    SMOKE_SENSOR("smokeSensor") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.GONE, R.string.smoke_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.smokeSensor, null))
            if (sensor.details == "true") {
                view.sensorDetalis.visibility = View.VISIBLE
                view.sensorDetalis.text = view.context.getString(R.string.smoke_detected)
                view.resources.getDrawable(R.drawable.smoke_detector, null).setTint(view.resources.getColor(R.color.alert))
            }
            view.sensorImage.setImageDrawable(view.resources.getDrawable(R.drawable.smoke_detector, null))
        }
    },
    WINDOW_BLIND("windowBlind") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.VISIBLE, R.string.blinds_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.windowBlindsSensor, null))
            view.sensorDetalis.text = view.context.getString(R.string.window_blinds_details, sensor.details)
            view.sensorImage.setImageDrawable(view.resources.getDrawable(R.drawable.window_blinds, null))
        }
    },
    WINDOW_SENSOR("windowSensor") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.VISIBLE, R.string.window_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.windowSensor, null))
            if (sensor.details == "true") {
                view.sensorDetalis.text = "Opened"
                view.sensorImage.setImageDrawable(view.resources.getDrawable(R.drawable.window_open, null))
            } else {
                view.sensorDetalis.text = "Closed"
                view.sensorImage.setImageDrawable(view.resources.getDrawable(R.drawable.window_closed, null))
            }
        }
    },
    RFID_SENSOR("RFIDSensor") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.GONE, R.string.RFID_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.rfidSensor, null))
            view.sensorImage.setImageDrawable(view.resources.getDrawable(R.drawable.rfid_sensor, null))
        }
    },
    HVAC_ROOM("HVACRoom") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.GONE, R.string.HVAC_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.hvacSensor, null))
            view.sensorImage.setImageDrawable(view.resources.getDrawable(R.drawable.hvac, null))
        }
    },
    HVAC_STATUS("HVACStatus") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            view.sensorName.text = "HVACA"
        }
    };

    abstract fun setAttributes(sensor: DashboardSensor, view: View)
    fun setTextAndVisibility(view: View, visibility: Int, nameSensor: Int) {
        view.sensorName.text = view.context.getString(nameSensor)
        view.sensorDetalis.visibility = visibility
    }
}
