package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.dashboard.model.api.authorization.AuthorizationInterceptor
import com.intive.patronage.smarthome.feature.dashboard.model.api.mock.MockInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val networkApiModule = module {

    single {
        OkHttpClient.Builder()
            .callTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(AuthorizationInterceptor())
//            .addInterceptor(MockInterceptor())
            .cache(null)
            .build()
    }
}