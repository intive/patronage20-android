package com.intive.patronage.smarthome.feature.settings.feature

import com.intive.patronage.smarthome.common.NOTIFICATIONS_VISIBILITY
import com.intive.patronage.smarthome.common.PreferencesWrapper
import com.intive.patronage.smarthome.feature.settings.SettingType

fun setupNotificationsVisibilitySwitch(preferencesWrapper: PreferencesWrapper) {
    SettingType.NOTIFICATIONS.isChecked = preferencesWrapper.getBooleanFromPreference(NOTIFICATIONS_VISIBILITY)
}

fun setNotificationsVisibility(notificationsVisibility: Boolean, preferencesWrapper: PreferencesWrapper) {
    preferencesWrapper.setNotificationsVisibilityPreference(notificationsVisibility)
}