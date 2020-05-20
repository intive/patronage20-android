package com.intive.patronage.smarthome.feature.settings

import android.view.View
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.settings_image_item.view.*
import kotlinx.android.synthetic.main.settings_switch_item.view.*

enum class SettingType(val type: String, val onlyDebug: Boolean = false, var isChecked: Boolean = true) {
    NIGHT_MODE("switch") {
        override fun setItemView(itemView: View) {
            itemView.settingSwitchText.text = itemView.resources.getString(R.string.dark_mode_setting)
            itemView.settingSwitchIcon.setBackgroundResource(R.drawable.dark_mode_icon)
            itemView.settingSwitch.visibility = View.VISIBLE
        }
    },
    DEVELOPER_SETTINGS("image", true) {
        override fun setItemView(itemView: View) {
            itemView.settingImageText.text = itemView.resources.getString(R.string.developer_settings)
            itemView.settingImageIcon.setBackgroundResource(R.drawable.developer_settings_icon)
            itemView.settingImage.visibility = View.VISIBLE
            itemView.settingImage.setBackgroundResource(R.drawable.extendable_setting_icon)
        }
    },
    NOTIFICATIONS("switch") {
        override fun setItemView(itemView: View) {
            itemView.settingSwitchText.text = itemView.resources.getString(R.string.notifications_setting)
            itemView.settingSwitchIcon.setBackgroundResource(R.drawable.notification_icon)
            itemView.settingSwitch.visibility = View.VISIBLE
        }
    },
    THIRD_PARTY_ACKNOWLEDGMENTS("image") {
        override fun setItemView(itemView: View) {
            itemView.settingImageText.text = itemView.resources.getString(R.string.third_party_acknowledgments)
            itemView.settingImageIcon.setBackgroundResource(R.drawable.info)
            itemView.settingImage.visibility = View.VISIBLE
            itemView.settingImage.setBackgroundResource(R.drawable.extendable_setting_icon)
        }
    },
    LEGAL_NOTICE("image") {
        override fun setItemView(itemView: View) {
            itemView.settingImageText.text = itemView.resources.getString(R.string.legal_notice)
            itemView.settingImageIcon.setBackgroundResource(R.drawable.ic_policy_24px)
            itemView.settingImage.visibility = View.VISIBLE
            itemView.settingImage.setBackgroundResource(R.drawable.extendable_setting_icon)
        }
    },
    SIGN_OUT("image") {
        override fun setItemView(itemView: View) {
            itemView.settingImageText.text = itemView.resources.getString(R.string.sign_out)
            itemView.settingImageIcon.setBackgroundResource(R.drawable.account)
            itemView.settingImage.visibility = View.VISIBLE
            itemView.settingImage.setBackgroundResource(R.drawable.exit)
        }
    };

    abstract fun setItemView(itemView: View)
}