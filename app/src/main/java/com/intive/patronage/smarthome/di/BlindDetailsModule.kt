package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.blind.view.BlindViewEventListener
import com.intive.patronage.smarthome.feature.blind.viewmodel.BlindDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val blindDetailsModule = module {
    viewModel { (blindViewEventListener: BlindViewEventListener, id: Int) -> BlindDetailsViewModel(get(), blindViewEventListener, id) }
}