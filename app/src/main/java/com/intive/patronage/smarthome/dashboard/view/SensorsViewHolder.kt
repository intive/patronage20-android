package com.intive.patronage.smarthome.dashboard.view

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.dashboard.model.Dashboard
import kotlinx.android.synthetic.main.sensor_list_item.view.*

class SensorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(sensor: Dashboard) {
        with(itemView) {

            sensor_name.text = sensor.lights.toString()
        }
    }
}