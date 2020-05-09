package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.light.model.api.LightDetailsService
import com.intive.patronage.smarthome.feature.light.view.ColorPickerEventListener
import com.intive.patronage.smarthome.feature.light.viewmodel.LightsDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val lightsDetailsModule = module {
    viewModel { (colorPickerEventListener: ColorPickerEventListener, id : Int) -> LightsDetailsViewModel(get(), get(), colorPickerEventListener, id) }
    single { LightDetailsService(get()) }
}