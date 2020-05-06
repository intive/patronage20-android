package com.intive.patronage.smarthome.feature.settings

import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.settings_list_item.view.*

enum class SettingClickEvent {
    NIGHT_MODE {
        override fun onClick(view: RecyclerView, dashboardCoordinator: DashboardCoordinator) {
            view.darkModeSwitch.isChecked = !view.darkModeSwitch.isChecked
        }
    },
    NOTIFICATIONS {
        override fun onClick(view: RecyclerView, dashboardCoordinator: DashboardCoordinator) {
            //TODO: implement behaviour
        }
    },
    DEVELOPER_SETTINGS {
        override fun onClick(view: RecyclerView, dashboardCoordinator: DashboardCoordinator) {
            dashboardCoordinator.goToDeveloperSettings()
        }
    };

    abstract fun onClick(view: RecyclerView, dashboardCoordinator: DashboardCoordinator)
}