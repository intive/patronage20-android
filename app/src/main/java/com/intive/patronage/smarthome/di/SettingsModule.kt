package com.intive.patronage.smarthome.di

import android.view.View
import com.intive.patronage.smarthome.feature.settings.SettingType
import com.intive.patronage.smarthome.feature.settings.SettingsListAdapter
import org.koin.dsl.module

val settingsModule = module {
    factory {
        (itemView: Array<SettingType>, onSettingClickListener: (settingType: SettingType, itemView: View) -> Unit)
            -> SettingsListAdapter(itemView, onSettingClickListener)
    }
}