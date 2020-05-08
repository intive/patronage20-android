package com.intive.patronage.smarthome

import android.view.View
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.intive.patronage.smarthome.feature.home.view.DialogSensorMock
import kotlinx.android.synthetic.main.dialog_sensor_list_item.view.*
import kotlinx.android.synthetic.main.sensor_list_item.view.*

enum class SensorDialogType(val type: String) {

    RGB_LIGHT("LED_CONTROLLER") {
        override fun setAttributes(sensor: HomeSensor, view: View) {
            setText(view, R.string.light_sensor_name)
        }
        override fun getPaintColor(): Int {
            return R.color.lightSensor
        }
    },
    TEMPERATURE_SENSOR("TEMPERATURE_SENSOR") {
        override fun setAttributes(sensor: HomeSensor, view: View) {
            setText(view, R.string.temperature_sensor_name)
        }
        override fun getPaintColor(): Int {
            return R.color.temperatureSensor
        }
    },
    SMOKE_SENSOR("smokeSensor") {
        override fun setAttributes(sensor: HomeSensor, view: View) {
            setText(view, R.string.smoke_sensor_name)
        }
        override fun getPaintColor(): Int {
            return R.color.smokeSensor
        }
    },
    WINDOW_BLIND("windowBlind") {
        override fun setAttributes(sensor: HomeSensor, view: View) {
            setText(view, R.string.blinds_sensor_name)
        }
        override fun getPaintColor(): Int {
            return R.color.windowBlindsSensor
        }
    },
    WINDOW_SENSOR("windowSensor") {
        override fun setAttributes(sensor: HomeSensor, view: View) {
            setText(view, R.string.window_sensor_name)
        }
        override fun getPaintColor(): Int {
            return R.color.windowSensor
        }
    },
    RFID_SENSOR("RFIDSensor") {
        override fun setAttributes(sensor: HomeSensor, view: View) {
            setText(view, R.string.RFID_sensor_name)
        }
        override fun getPaintColor(): Int {
            return R.color.rfidSensor
        }
    },
    HVAC_ROOM("HVACRoom") {
        override fun setAttributes(sensor: HomeSensor, view: View) {
            setText(view, R.string.HVAC_sensor_name)
        }

        override fun getPaintColor(): Int {
            return R.color.hvacSensor
        }
    };

    abstract fun getPaintColor(): Int
    abstract fun setAttributes(sensor: HomeSensor, view: View)
    fun setText(view: View, nameSensor: Int) {
        view.dialogSensorName.text = view.context.getString(nameSensor)
    }
}