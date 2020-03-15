package com.intive.patronage.smarthome.dashboard.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.dashboard.model.DashboardSensor
import kotlinx.android.synthetic.main.sensor_list_item.view.*

class SensorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(sensor: DashboardSensor) {
        with(itemView) {
            sensor_name.text = sensor.type
        }
    }
}