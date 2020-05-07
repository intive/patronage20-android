package com.intive.patronage.smarthome.feature.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R

class SettingsListAdapter(
    private val onSettingClickListener: (settingType: SettingType) -> Unit
) : RecyclerView.Adapter<SettingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val itemView = when (viewType) {
            0 -> LayoutInflater.from(parent.context).inflate(R.layout.settings_switch_item, parent, false)
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.settings_image_item, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.settings_image_item, parent, false)
        }
        return SettingsViewHolder(itemView)
    }

    override fun getItemViewType(position: Int) = when(SettingType.values()[position].type) {
        "switch" -> 0
        "image" -> 1
        else -> 1
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bindView(position, onSettingClickListener)
    }

    override fun getItemCount() = SettingType.values().size
}