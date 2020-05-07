package com.intive.patronage.smarthome.feature.settings

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.BuildConfig
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.settings_list_item.view.*

enum class SettingClickEvent {
    NIGHT_MODE {
        override fun onClick(view: RecyclerView, dashboardCoordinator: DashboardCoordinator, context: Context) {
            view.darkModeSwitch.isChecked = !view.darkModeSwitch.isChecked
        }
    },
    NOTIFICATIONS {
        override fun onClick(view: RecyclerView, dashboardCoordinator: DashboardCoordinator, context: Context) {
            view.notificationsSwitch.isChecked = !view.notificationsSwitch.isChecked
        }
    },
    DEVELOPER_SETTINGS {
        override fun onClick(view: RecyclerView, dashboardCoordinator: DashboardCoordinator, context: Context) {
            if (BuildConfig.DEBUG) {
                dashboardCoordinator.goToDeveloperSettings()
            } else {
                Toast.makeText(context,"You must be in debug mode", Toast.LENGTH_LONG).show()
            }
        }
    };

    abstract fun onClick(view: RecyclerView, dashboardCoordinator: DashboardCoordinator, context: Context)
}