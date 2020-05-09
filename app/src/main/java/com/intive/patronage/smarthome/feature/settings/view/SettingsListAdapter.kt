package com.intive.patronage.smarthome.feature.settings.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.settings.SettingType
import kotlinx.android.synthetic.main.settings_switch_item.view.*

class SettingsListAdapter(
    private val itemList: Array<SettingType>,
    private val onSettingClickListener: (settingType: SettingType, itemView: View) -> Unit
) : RecyclerView.Adapter<SettingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return SettingsViewHolder(itemView, itemList)
    }

    override fun getItemViewType(position: Int) = when(itemList[position].type) {
        "switch" -> R.layout.settings_switch_item
        "image" -> R.layout.settings_image_item
        else -> R.layout.settings_image_item
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bindView(position, onSettingClickListener)

        if (itemList[position].type == "switch") {
            holder.itemView.settingSwitch.isChecked = itemList[position].isChecked
        }
    }

    override fun getItemCount() = itemList.size
}