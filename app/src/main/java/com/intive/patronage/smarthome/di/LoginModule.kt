package com.intive.patronage.smarthome.di

import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.feature.login.authentication.AuthenticationService
import com.intive.patronage.smarthome.feature.login.view.LoginEventListener
import com.intive.patronage.smarthome.feature.login.view.RegisterEventListener
import com.intive.patronage.smarthome.feature.login.viewmodel.LoginViewModel
import com.intive.patronage.smarthome.feature.login.viewmodel.RegisterViewModel
import com.intive.patronage.smarthome.navigator.LoginCoordinator
import com.intive.patronage.smarthome.navigator.Navigator
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val loginModule: Module = module {

    factory { (activity: AppCompatActivity) ->
        LoginCoordinator(Navigator(activity, get())) }

    factory { (appCompatActivity: AppCompatActivity) ->
        AuthenticationService(appCompatActivity, LoginCoordinator(Navigator(appCompatActivity,get()))) }

    viewModel { (authenticationService: AuthenticationService, loginEventListener: LoginEventListener) ->
        LoginViewModel(authenticationService, loginEventListener) }

    viewModel { (authenticationService: AuthenticationService, registerEventListener: RegisterEventListener) ->
        RegisterViewModel(authenticationService, registerEventListener) }
}


