package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.temperature.viewmodel.TemperatureDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val temperatureDetailsModule = module {
    viewModel { TemperatureDetailsViewModel(get()) }
}