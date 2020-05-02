package com.intive.patronage.smarthome.notifications.di

import com.intive.patronage.smarthome.feature.dashboard.model.api.authorization.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val notificationsHttpClientModule = module {
    single {
        OkHttpClient.Builder()
            .callTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(AuthorizationInterceptor())
            .cache(null)
            .build()
    }
}