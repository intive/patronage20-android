package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.blind.view.BlindView
import com.intive.patronage.smarthome.feature.blind.viewmodel.BlindDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val blindDetailsModule = module {
    viewModel { (canvas: BlindView, id: Int) -> BlindDetailsViewModel(get(), canvas, id) }
}