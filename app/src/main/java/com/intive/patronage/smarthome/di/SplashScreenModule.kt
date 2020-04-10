package com.intive.patronage.smarthome.di

import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.common.SmartHomeAlertDialog
import com.intive.patronage.smarthome.common.SmartHomeErrorSnackbar
import com.intive.patronage.smarthome.feature.splashcreen.viewmodel.SplashScreenViewModel
import com.intive.patronage.smarthome.navigator.Navigator
import com.intive.patronage.smarthome.navigator.SplashScreenCoordinator
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val splashScreenModule: Module = module {
    single { SmartHomeAlertDialog() }
    factory { (activity: AppCompatActivity) -> SmartHomeErrorSnackbar(activity) }
    viewModel {
        SplashScreenViewModel(
            get()
        )
    }
    factory { Navigator(get()) }
    factory { (activity: AppCompatActivity) -> SplashScreenCoordinator(Navigator(activity)) }
}


