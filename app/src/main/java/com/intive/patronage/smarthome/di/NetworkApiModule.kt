package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.dashboard.model.api.authorization.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val networkApiModule = module {

    single {
        OkHttpClient.Builder()
            .readTimeout(180, TimeUnit.SECONDS)
            .callTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY })
            .cache(null)
            .build()
    }
}