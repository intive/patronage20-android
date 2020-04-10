package com.intive.patronage.smarthome.di

import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.view.SensorsListAdapter
import com.intive.patronage.smarthome.feature.dashboard.viewmodel.DashboardViewModel
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import com.intive.patronage.smarthome.navigator.Navigator
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val dashboardModule = module {
    factory { (onItemClickListener: (sensor: DashboardSensor)-> Unit) -> SensorsListAdapter(onItemClickListener) }
    viewModel { DashboardViewModel(get()) }
    factory { (activity: AppCompatActivity) -> DashboardCoordinator(Navigator(activity, get())) }
}