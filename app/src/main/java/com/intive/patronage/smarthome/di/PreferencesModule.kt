package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.common.PreferencesWrapper
import org.koin.dsl.module

val preferencesModule = module {
    single { PreferencesWrapper(get()) }
}