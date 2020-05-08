package com.intive.patronage.smarthome.feature.settings

import android.content.Context
import android.view.View
import android.widget.Toast
import com.intive.patronage.smarthome.BuildConfig
import com.intive.patronage.smarthome.common.PreferencesWrapper
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.settings_switch_item.view.*

enum class SettingClickEvent {
    NIGHT_MODE {
        override fun onClick(itemView: View, dashboardCoordinator: DashboardCoordinator, context: Context, preferences: PreferencesWrapper) {
            with(!itemView.settingSwitch.isChecked) {
                itemView.settingSwitch.isChecked = this
                SettingType.NIGHT_MODE.isChecked = this
                setDarkMode(this, preferences)
            }
        }
    },
    DEVELOPER_SETTINGS {
        override fun onClick(itemView: View, dashboardCoordinator: DashboardCoordinator, context: Context, preferences: PreferencesWrapper) {
            if (BuildConfig.DEBUG) {
                dashboardCoordinator.goToDeveloperSettings()
            } else {
                Toast.makeText(context,"You must be in debug mode", Toast.LENGTH_LONG).show()
            }
        }
    },
    NOTIFICATIONS {
        override fun onClick(itemView: View, dashboardCoordinator: DashboardCoordinator, context: Context, preferences: PreferencesWrapper) {
            itemView.settingSwitch.isChecked = !itemView.settingSwitch.isChecked
            SettingType.NOTIFICATIONS.isChecked = itemView.settingSwitch.isChecked
        }
    };

    abstract fun onClick(itemView: View, dashboardCoordinator: DashboardCoordinator, context: Context, preferences: PreferencesWrapper)
}