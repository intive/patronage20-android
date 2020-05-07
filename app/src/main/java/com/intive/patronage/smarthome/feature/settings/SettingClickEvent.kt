package com.intive.patronage.smarthome.feature.settings

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.BuildConfig
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.settings_switch_item.view.*

enum class SettingClickEvent {
    NIGHT_MODE {
        override fun onClick(view: View, dashboardCoordinator: DashboardCoordinator, context: Context) {
            view.settingSwitch.isChecked = !view.settingSwitch.isChecked
        }
    },
    NOTIFICATIONS {
        override fun onClick(view: View, dashboardCoordinator: DashboardCoordinator, context: Context) {
            view.settingSwitch.isChecked = !view.settingSwitch.isChecked
        }
    },
    A {
        override fun onClick(view: View, dashboardCoordinator: DashboardCoordinator, context: Context) {
            view.settingSwitch.isChecked = !view.settingSwitch.isChecked
        }
    },
    B {
        override fun onClick(view: View, dashboardCoordinator: DashboardCoordinator, context: Context) {
            view.settingSwitch.isChecked = !view.settingSwitch.isChecked
        }
    },
    C {
        override fun onClick(view: View, dashboardCoordinator: DashboardCoordinator, context: Context) {
            view.settingSwitch.isChecked = !view.settingSwitch.isChecked
        }
    },
    DEVELOPER_SETTINGS {
        override fun onClick(view: View, dashboardCoordinator: DashboardCoordinator, context: Context) {
            if (BuildConfig.DEBUG) {
                dashboardCoordinator.goToDeveloperSettings()
            } else {
                Toast.makeText(context,"You must be in debug mode", Toast.LENGTH_LONG).show()
            }
        }
    };

    abstract fun onClick(view: View, dashboardCoordinator: DashboardCoordinator, context: Context)
}