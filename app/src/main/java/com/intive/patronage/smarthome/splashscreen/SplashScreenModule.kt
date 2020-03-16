package com.intive.patronage.smarthome.splashscreen

import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.navigator.Navigator
import com.intive.patronage.smarthome.navigator.SplashScreenCoordinator
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val splashScreenModule: Module = module {
    single { SmartHomeAlertDialog() }
    viewModel { SplashScreenViewModel(get()) }
    single { Navigator(get()) }
    single { (activity: AppCompatActivity) -> SplashScreenCoordinator(Navigator(activity)) }
}


