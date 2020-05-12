package com.intive.patronage.smarthome.feature.settings

import com.intive.patronage.smarthome.common.NOTIFICATIONS_VISIBILITY
import com.intive.patronage.smarthome.common.PreferencesWrapper

fun setupNotificationsVisibilitySwitch(preferencesWrapper: PreferencesWrapper) {
    SettingType.NOTIFICATIONS.isChecked = preferencesWrapper.getBooleanFromPreference(NOTIFICATIONS_VISIBILITY)
}

fun setNotificationsVisibility(notificationsVisibility: Boolean, preferencesWrapper: PreferencesWrapper) {
    preferencesWrapper.setNotificationsVisibilityPreference(notificationsVisibility)
}