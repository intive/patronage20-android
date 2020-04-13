package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.AnalyticsWrapper
import org.koin.dsl.module

val analyticsModule = module {
    single { AnalyticsWrapper(get()) }
}