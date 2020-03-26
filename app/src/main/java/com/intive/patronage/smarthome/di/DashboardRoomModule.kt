package com.intive.patronage.smarthome.di

import androidx.room.Room
import com.intive.patronage.smarthome.feature.dashboard.model.api.room.database.DashboardDatabase
import com.intive.patronage.smarthome.feature.dashboard.model.api.room.repository.DashboardRoomRepository
import com.intive.patronage.smarthome.feature.dashboard.model.api.room.repository.DashboardRoomRepositoryAPI
import org.koin.dsl.module

val dashboardRoomModule = module {
    single {
        Room.databaseBuilder(get(), DashboardDatabase::class.java, "dashboard_database").build()
    }
    single { get<DashboardDatabase>().dashboardDao() }
    single<DashboardRoomRepositoryAPI> { DashboardRoomRepository(get()) }
}