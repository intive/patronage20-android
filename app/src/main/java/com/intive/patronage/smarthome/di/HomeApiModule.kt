package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.home.model.api.service.HomeService
import org.koin.dsl.module

val homeApiModule = module {

    single { HomeService(get()) }

}