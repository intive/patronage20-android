package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.home.view.SensorDialogListAdapter
import com.intive.patronage.smarthome.feature.home.viewmodel.HomeSharedViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { (onItemClickListener: (sensor: DashboardSensor) -> Unit) ->
        SensorDialogListAdapter(
            onItemClickListener
        )
    }
    viewModel { HomeSharedViewModel(get(), get()) }
}