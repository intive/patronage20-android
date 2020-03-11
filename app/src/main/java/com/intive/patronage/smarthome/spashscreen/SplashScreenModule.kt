package com.intive.patronage.smarthome.spashscreen

import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import com.intive.patronage.smarthome.navigator.Navigator
import com.intive.patronage.smarthome.spashscreen.forTestOnly.PostFetcher
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val splashScreenModule: Module = module {
    factory { SmartHomeAlertDialog() }
    factory { PostFetcher() }
    viewModel { SplashScreenViewModel() }
   // factory { Navigator(get())}
    single { DashboardCoordinator(get()) }
}