package com.intive.patronage.smarthome.spashscreen

import com.intive.patronage.smarthome.spashscreen.forTestOnly.PostFetcher
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val splashScreenModule: Module = module {
    factory { CustomAlertDialog() }
    factory { PostFetcher() }
    viewModel { SplashScreenViewModel() }
}