package com.intive.patronage.smarthome.di

import android.view.View
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.home.view.HomeSensorListAdapter
import com.intive.patronage.smarthome.feature.home.viewmodel.HomeSharedViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { (onItemClickListener: (sensor: DashboardSensor, x: Float, y: Float, draggedView: View) -> Unit) ->
        HomeSensorListAdapter(
            onItemClickListener
        ) }
    viewModel { HomeSharedViewModel(get(), get()) }
}