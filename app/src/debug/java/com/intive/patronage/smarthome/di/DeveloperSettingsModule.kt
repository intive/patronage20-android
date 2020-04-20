package com.intive.patronage.smarthome.di

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import com.intive.patronage.smarthome.DeveloperSettings
import com.intive.patronage.smarthome.feature.developer.model.DeveloperSettingsService
import com.intive.patronage.smarthome.feature.developer.viewmodel.DeveloperSettingsViewModel
import com.intive.patronage.smarthome.navigator.DeveloperSettingsCoordinator
import com.intive.patronage.smarthome.navigator.Navigator
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val developerSettingsModule = module {
    viewModel {
        DeveloperSettingsViewModel(
            get(),
            get()
        )
    }
    single { DeveloperSettings() }
    factory { (activity : AppCompatActivity) -> DeveloperSettingsCoordinator(Navigator(activity, get())) }

    single { DeveloperSettingsService( get() ) }

}