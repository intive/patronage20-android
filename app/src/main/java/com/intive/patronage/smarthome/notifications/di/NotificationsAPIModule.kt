package com.intive.patronage.smarthome.notifications.di

import com.intive.patronage.smarthome.notifications.api.NotificationsAPI
import com.intive.patronage.smarthome.notifications.api.NotificationsAPIFactory
import com.intive.patronage.smarthome.notifications.service.NotificationsService
import org.koin.dsl.module
import retrofit2.Retrofit

val notificationsAPIModule = module {
    single {
        NotificationsAPIFactory(get()).buildRetrofitClient()
    }

    single {
        get<Retrofit>().create(NotificationsAPI::class.java)
    }
}