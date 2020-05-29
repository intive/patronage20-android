package com.intive.patronage.smarthome

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import kotlinx.android.synthetic.main.sensor_list_item.view.*
import kotlin.math.roundToInt

enum class SensorType(val type: String) {

    RGB_LIGHT("LED_CONTROLLER") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.GONE, R.string.light_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.lightSensor, null))
            view.resources.getDrawable(R.drawable.light_bulb_inside, null)
                .setTint(sensor.details.toInt())
            view.sensorImage.setImageDrawable(
                view.resources.getDrawable(
                    R.drawable.light,
                    null
                ).mutate()
            )
        }

        override fun getDrawable(sensor: DashboardSensor, resources: Resources): Drawable? {
            return resources.getDrawable(R.drawable.light_bulb_outline, null).mutate()
        }
    },
    TEMPERATURE_SENSOR("TEMPERATURE_SENSOR") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            val temperature: Float =
                ((sensor.details.toFloat() * 10.0).roundToInt() / 10.0).toFloat()
            setTextAndVisibility(view, View.VISIBLE, R.string.temperature_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.temperatureSensor, null))
            view.sensorDetalis.text =
                view.context.getString(R.string.temperature_details, temperature.toString())
            view.sensorImage.setImageDrawable(
                view.resources.getDrawable(
                    R.drawable.thermometer,
                    null
                )
            )
        }

        override fun getDrawable(sensor: DashboardSensor, resources: Resources): Drawable? {
            return resources.getDrawable(R.drawable.thermometer, null).mutate()
        }
    },
    SMOKE_SENSOR("smokeSensor") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.GONE, R.string.smoke_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.smokeSensor, null))
            if (sensor.details == "true") {
                view.sensorDetalis.visibility = View.VISIBLE
                view.sensorDetalis.text = view.context.getString(R.string.smoke_detected)
                view.resources.getDrawable(R.drawable.smoke_detector, null)
                    .setTint(view.resources.getColor(R.color.alert))
            }
            view.sensorImage.setImageDrawable(
                view.resources.getDrawable(
                    R.drawable.smoke_detector,
                    null
                )
            )
        }

        override fun getDrawable(sensor: DashboardSensor, resources: Resources): Drawable? {
            return resources.getDrawable(R.drawable.smoke_detector, null).mutate()
        }
    },
    WINDOW_BLIND("windowBlind") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.VISIBLE, R.string.blinds_sensor_name)
            view.sensorFlag.setColorFilter(
                view.resources.getColor(
                    R.color.windowBlindsSensor,
                    null
                )
            )
            view.sensorDetalis.text =
                view.context.getString(R.string.window_blinds_details, sensor.details)
            view.sensorImage.setImageDrawable(
                view.resources.getDrawable(
                    R.drawable.window_blinds,
                    null
                )
            )
        }

        override fun getDrawable(sensor: DashboardSensor, resources: Resources): Drawable? {
            return resources.getDrawable(R.drawable.window_blinds, null).mutate()
        }
    },
    WINDOW_SENSOR("windowSensor") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.VISIBLE, R.string.window_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.windowSensor, null))
            if (sensor.details == "open") {
                view.sensorDetalis.text = view.resources.getString(R.string.opened_window)
                view.sensorImage.setImageDrawable(
                    view.resources.getDrawable(
                        R.drawable.window_open,
                        null
                    )
                )
            } else {
                view.sensorDetalis.text = view.resources.getString(R.string.closed_window)
                view.sensorImage.setImageDrawable(
                    view.resources.getDrawable(
                        R.drawable.window_closed,
                        null
                    )
                )
            }
        }

        override fun getDrawable(sensor: DashboardSensor, resources: Resources): Drawable? {
            if (sensor.details == "open") {
                return resources.getDrawable(R.drawable.window_open, null).mutate()
            }
            return resources.getDrawable(R.drawable.window_closed, null).mutate()
        }
    },
    RFID_SENSOR("RFIDSensor") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.GONE, R.string.RFID_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.rfidSensor, null))
            view.sensorImage.setImageDrawable(
                view.resources.getDrawable(
                    R.drawable.rfid_sensor,
                    null
                )
            )
        }

        override fun getDrawable(sensor: DashboardSensor, resources: Resources): Drawable? {
            return resources.getDrawable(R.drawable.rfid_sensor, null).mutate()
        }
    },
    HVAC_ROOM("HVACRoom") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            setTextAndVisibility(view, View.GONE, R.string.HVAC_sensor_name)
            view.sensorFlag.setColorFilter(view.resources.getColor(R.color.hvacSensor, null))
            view.sensorImage.setImageDrawable(view.resources.getDrawable(R.drawable.hvac, null))
        }

        override fun getDrawable(sensor: DashboardSensor, resources: Resources): Drawable? {
            return resources.getDrawable(R.drawable.hvac, null).mutate()
        }
    },
    HVAC_STATUS("HVACStatus") {
        override fun setAttributes(sensor: DashboardSensor, view: View) {
            view.sensorName.text = "HVACA"
        }

        override fun getDrawable(sensor: DashboardSensor, resources: Resources): Drawable? {
            return null
        }
    };

    abstract fun setAttributes(sensor: DashboardSensor, view: View)
    abstract fun getDrawable(sensor: DashboardSensor, resources: Resources): Drawable?
    fun setTextAndVisibility(view: View, visibility: Int, nameSensor: Int) {
        view.sensorName.text = view.context.getString(nameSensor)
        view.sensorDetalis.visibility = visibility
    }
}
