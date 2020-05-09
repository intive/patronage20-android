package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.blind.model.api.BlindDetailsService
import com.intive.patronage.smarthome.feature.blind.view.BlindViewEventListener
import com.intive.patronage.smarthome.feature.blind.viewmodel.BlindDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val blindDetailsModule = module {
    single { BlindDetailsService(get()) }

    viewModel { (blindViewEventListener: BlindViewEventListener, id: Int) -> BlindDetailsViewModel(get(), blindViewEventListener, id, get()) }
}