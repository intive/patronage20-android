package com.intive.patronage.smarthome.feature.settings

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(
        position: Int,
        onSettingClickListener: (settingType: SettingType) -> Unit
    ) {
        itemView.setOnClickListener {
            onSettingClickListener(SettingType.values()[position])
        }
        SettingType.values()[position].setItemView(itemView)
    }
}