package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.dashboard.viewmodel.SmartHomeFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val smartHomeFramgentModule = module{
    viewModel { SmartHomeFragmentViewModel(get()) }
}