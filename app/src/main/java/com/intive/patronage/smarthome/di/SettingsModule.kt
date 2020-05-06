package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.settings.SettingType
import com.intive.patronage.smarthome.feature.settings.SettingsListAdapter
import org.koin.dsl.module

val settingsModule = module {
    factory { (onSettingClickListener: (settingType: SettingType) -> Unit) -> SettingsListAdapter(onSettingClickListener) }
}