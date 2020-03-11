package com.intive.patronage.smarthome.spashscreen

import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.navigator.Navigator
import com.intive.patronage.smarthome.navigator.SplashScreenCoordinator
import com.intive.patronage.smarthome.spashscreen.forTestOnly.PostFetcher
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val splashScreenModule: Module = module {
    factory { SmartHomeAlertDialog() }
    factory { PostFetcher() }
    viewModel { SplashScreenViewModel() }
    single { Navigator(get()) }
    single {(activity:AppCompatActivity ) -> SplashScreenCoordinator(Navigator(activity)) }
}


