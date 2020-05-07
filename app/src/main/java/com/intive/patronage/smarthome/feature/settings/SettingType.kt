package com.intive.patronage.smarthome.feature.settings

import android.view.View
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.settings_image_item.view.*
import kotlinx.android.synthetic.main.settings_switch_item.view.*

enum class SettingType(val type: String) {
    NIGHT_MODE("switch") {
        override fun setItemView(itemView: View) {
            itemView.settingSwitchText.text = itemView.resources.getString(R.string.dark_mode_setting)
            itemView.settingSwitchIcon.setBackgroundResource(R.drawable.dark_mode_icon)
            itemView.settingSwitch.visibility = View.VISIBLE
        }
    },
    NOTIFICATIONS("switch") {
        override fun setItemView(itemView: View) {
            itemView.settingSwitchText.text = itemView.resources.getString(R.string.notifications_setting)
            itemView.settingSwitchIcon.setBackgroundResource(R.drawable.notification_icon)
            itemView.settingSwitch.visibility = View.VISIBLE
        }
    },
    A("switch") {
        override fun setItemView(itemView: View) {
            itemView.settingSwitchText.text = itemView.resources.getString(R.string.notifications_setting)
            itemView.settingSwitchIcon.setBackgroundResource(R.drawable.notification_icon)
            itemView.settingSwitch.visibility = View.VISIBLE
        }
    },
    B("switch") {
        override fun setItemView(itemView: View) {
            itemView.settingSwitchText.text = itemView.resources.getString(R.string.notifications_setting)
            itemView.settingSwitchIcon.setBackgroundResource(R.drawable.notification_icon)
            itemView.settingSwitch.visibility = View.VISIBLE
        }
    },
    C("switch") {
        override fun setItemView(itemView: View) {
            itemView.settingSwitchText.text = itemView.resources.getString(R.string.notifications_setting)
            itemView.settingSwitchIcon.setBackgroundResource(R.drawable.notification_icon)
            itemView.settingSwitch.visibility = View.VISIBLE
        }
    },
    DEVELOPER_SETTINGS("image") {
        override fun setItemView(itemView: View) {
            itemView.settingImageText.text = itemView.resources.getString(R.string.developer_settings)
            itemView.settingImageIcon.setBackgroundResource(R.drawable.developer_settings_icon)
            itemView.settingImage.visibility = View.VISIBLE
            itemView.settingImage.setBackgroundResource(R.drawable.extendable_setting_icon)
        }
    };

    abstract fun setItemView(itemView: View)
}