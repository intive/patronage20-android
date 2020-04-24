package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import com.intive.patronage.smarthome.feature.home.view.SensorDialogListAdapter
import com.intive.patronage.smarthome.feature.home.viewmodel.SensorDialogViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { (onItemClickListener: (sensor: HomeSensor) -> Unit) ->
        SensorDialogListAdapter(
            onItemClickListener
        )
    }
    viewModel { SensorDialogViewModel(get()) }
}