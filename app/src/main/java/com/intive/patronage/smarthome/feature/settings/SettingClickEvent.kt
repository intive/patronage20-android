package com.intive.patronage.smarthome.feature.settings

import android.view.View
import com.intive.patronage.smarthome.common.PreferencesWrapper
import com.intive.patronage.smarthome.feature.settings.feature.setDarkMode
import com.intive.patronage.smarthome.feature.settings.feature.setNotificationsVisibility
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.settings_switch_item.view.*

enum class SettingClickEvent {
    NIGHT_MODE {
        override fun onClick(itemView: View, dashboardCoordinator: DashboardCoordinator, preferences: PreferencesWrapper) {
            with(!itemView.settingSwitch.isChecked) {
                itemView.settingSwitch.isChecked = this
                SettingType.NIGHT_MODE.isChecked = this
                setDarkMode(
                    this,
                    preferences
                )
            }
        }
    },
    DEVELOPER_SETTINGS {
        override fun onClick(itemView: View, dashboardCoordinator: DashboardCoordinator, preferences: PreferencesWrapper) {
            dashboardCoordinator.goToDeveloperSettings()
        }
    },
    NOTIFICATIONS {
        override fun onClick(itemView: View, dashboardCoordinator: DashboardCoordinator, preferences: PreferencesWrapper) {
            with(!itemView.settingSwitch.isChecked) {
                itemView.settingSwitch.isChecked = this
                SettingType.NOTIFICATIONS.isChecked = this
                setNotificationsVisibility(
                    this,
                    preferences
                )
            }
        }
    },
    THIRD_PARTY_ACKNOWLEDGMENTS {
        override fun onClick(itemView: View, dashboardCoordinator: DashboardCoordinator, preferences: PreferencesWrapper) {
            dashboardCoordinator.goToThirdPartyAcknowledgments()
        }
    };

    abstract fun onClick(itemView: View, dashboardCoordinator: DashboardCoordinator, preferences: PreferencesWrapper)
}