package com.intive.patronage.smarthome.di

import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.feature.login.GoogleLogin
import com.intive.patronage.smarthome.navigator.LoginCoordinator
import com.intive.patronage.smarthome.navigator.Navigator
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.math.log

val loginModule: Module = module {

    factory { (activity: AppCompatActivity) -> LoginCoordinator(Navigator(activity)) }
    factory { (appCompatActivity: AppCompatActivity) -> getGoogleLogin(appCompatActivity) }
}


fun getGoogleLogin(appCompatActivity: AppCompatActivity) : GoogleLogin {
    return GoogleLogin(appCompatActivity, LoginCoordinator(Navigator(appCompatActivity)))
}