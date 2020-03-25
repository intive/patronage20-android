package com.intive.smarthome.di

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.developer.settings.DeveloperSettings
import com.intive.patronage.smarthome.developer.settings.viewmodel.DeveloperSettingsViewModel
import com.intive.patronage.smarthome.navigator.DeveloperSettingsCoordinator
import com.intive.patronage.smarthome.navigator.Navigator
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.scope.viewModel
import org.koin.dsl.module

val developerSettingsModule = module {
    viewModel { DeveloperSettingsViewModel(get(), get()) }
    single { DeveloperSettings() }
    single<ObservableField<String>> { ObservableField() }
    factory { (activity : AppCompatActivity) -> DeveloperSettingsCoordinator(Navigator(activity)) }
}