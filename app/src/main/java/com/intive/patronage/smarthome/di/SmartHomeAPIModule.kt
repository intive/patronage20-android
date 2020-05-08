package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.api.SmartHomeAPI
import com.intive.patronage.smarthome.api.SmartHomeAPIFactory
import com.intive.patronage.smarthome.notifications.api.NotificationsAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val smartHomeAPIModule = module {
    single {
        SmartHomeAPIFactory(get()).buildRetrofitClient()
    }

    single {
        get<Retrofit>().create(SmartHomeAPI::class.java)
    }

    single {
        get<Retrofit>().create(NotificationsAPI::class.java)
    }
}