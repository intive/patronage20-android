package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.light.viewmodel.LightsDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val lightsDetailsModule = module {
    viewModel { (id : Int) -> LightsDetailsViewModel(get(), id) }
}