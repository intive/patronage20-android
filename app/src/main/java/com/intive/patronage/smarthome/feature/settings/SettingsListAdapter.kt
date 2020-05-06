package com.intive.patronage.smarthome.feature.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R

class SettingsListAdapter(
    private val onSettingClickListener: (settingType: SettingType) -> Unit
) : RecyclerView.Adapter<SettingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.settings_list_item, parent, false) as View
        return SettingsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bindView(position, onSettingClickListener)
    }

    override fun getItemCount() = SettingType.values().size
}