package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.home.view.DialogSensorMock
import com.intive.patronage.smarthome.feature.home.view.SensorDialogListAdapter
import com.intive.patronage.smarthome.feature.home.viewmodel.SensorDialogViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { SensorDialogListAdapter() }
    viewModel { SensorDialogViewModel() }
}