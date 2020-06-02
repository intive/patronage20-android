package com.intive.patronage.smarthome.feature.home.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.SensorType
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import kotlinx.android.synthetic.main.home_sensor_list_item.view.*

class HomeSensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(
        sensor: DashboardSensor,
        onHomeSensorClickListener: (sensor: DashboardSensor, x: Float, y: Float, draggedView: View) -> Unit
    ) {
        itemView.setOnLongClickListener {
            onHomeSensorClickListener(sensor, it.x, it.y, itemView)
            true
        }
        val drawable = SensorType.values().find {
            it.type == sensor.type
        }?.getDrawable(sensor, itemView.resources)?.mutate()
        drawable?.setTint(itemView.resources.getColor(R.color.text, null))
        with(itemView) {
            sensorIcon.setImageDrawable(drawable)
        }
    }
}