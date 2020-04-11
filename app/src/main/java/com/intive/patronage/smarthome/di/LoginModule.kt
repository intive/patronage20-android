package com.intive.patronage.smarthome.di

import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.feature.login.LoginGoogle
import com.intive.patronage.smarthome.navigator.LoginCoordinator
import com.intive.patronage.smarthome.navigator.Navigator
import org.koin.core.module.Module
import org.koin.dsl.module

val loginModule: Module = module {

    factory { (activity: AppCompatActivity) -> LoginCoordinator(Navigator(activity, get())) }
    factory { (appCompatActivity: AppCompatActivity) -> LoginGoogle(appCompatActivity, LoginCoordinator(Navigator(appCompatActivity,get()))) }
}


