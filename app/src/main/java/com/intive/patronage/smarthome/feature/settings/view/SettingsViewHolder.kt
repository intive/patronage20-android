package com.intive.patronage.smarthome.feature.settings.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.feature.settings.SettingType

class SettingsViewHolder(itemView: View, private val itemList: Array<SettingType>) : RecyclerView.ViewHolder(itemView) {

    fun bindView(
        position: Int,
        onSettingClickListener: (settingType: SettingType, itemView: View) -> Unit
    ) {
        itemView.setOnClickListener {
            onSettingClickListener(itemList[position], itemView)
        }
        itemList[position].setItemView(itemView)
    }
}