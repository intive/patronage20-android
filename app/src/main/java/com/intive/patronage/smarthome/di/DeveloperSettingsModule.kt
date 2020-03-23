package com.intive.patronage.smarthome.di

import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.navigator.DeveloperSettingsCoordinator
import com.intive.patronage.smarthome.navigator.Navigator
import org.koin.dsl.module

val developerSettingsModule = module {
    factory { (activity : AppCompatActivity) -> DeveloperSettingsCoordinator(Navigator(activity)) }
}