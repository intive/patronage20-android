package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.dashboard.model.api.respository.DashboardRepositoryAPI
import com.intive.patronage.smarthome.dashboard.model.api.respository.LocalDashboardRepository
import com.intive.patronage.smarthome.dashboard.model.api.service.DashboardService
import org.koin.dsl.module

val dashboardApiModule = module {

    single<DashboardRepositoryAPI> { LocalDashboardRepository() }

    single { DashboardService(get(), get()) }
}