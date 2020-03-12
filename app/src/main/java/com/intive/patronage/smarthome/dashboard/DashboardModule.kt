package com.intive.patronage.smarthome.dashboard

import com.intive.patronage.smarthome.dashboard.view.SensorsListAdapter
import com.intive.patronage.smarthome.dashboard.viewmodel.DashboardViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val dashboardModule = module {
    factory { SensorsListAdapter() }
    viewModel { DashboardViewModel() }
}