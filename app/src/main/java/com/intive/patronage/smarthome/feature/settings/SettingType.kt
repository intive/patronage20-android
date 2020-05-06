package com.intive.patronage.smarthome.feature.settings

import android.view.View
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.settings_list_item.view.*

enum class SettingType {
    NIGHT_MODE {
        override fun setItemView(itemView: View) {
            itemView.setting_text.text = itemView.resources.getString(R.string.dark_mode_setting)
            itemView.settingIcon.setBackgroundResource(R.drawable.dark_mode_icon)
            itemView.darkModeSwitch.visibility = View.VISIBLE
        }
    },
    NOTIFICATIONS {
        override fun setItemView(itemView: View) {
            itemView.setting_text.text = itemView.resources.getString(R.string.notifications_setting)
            itemView.settingIcon.setBackgroundResource(R.drawable.notification_icon)
            itemView.notificationsCheckBox.visibility = View.VISIBLE
        }
    },
    DEVELOPER_SETTINGS {
        override fun setItemView(itemView: View) {
            itemView.setting_text.text = itemView.resources.getString(R.string.developer_settings)
            itemView.settingIcon.setBackgroundResource(R.drawable.developer_settings_icon)
            itemView.settingActionIcon.visibility = View.VISIBLE
            itemView.settingActionIcon.setBackgroundResource(R.drawable.extendable_setting_icon)
        }
    };

    abstract fun setItemView(itemView: View)
}