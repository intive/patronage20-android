package com.intive.patronage.smarthome.dashboard.di

import com.intive.patronage.smarthome.dashboard.viewmodel.LightsDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val lightsDetailsModule = module {
    viewModel { LightsDetailsViewModel() }
}