package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.hvac.viewmodel.HVACViewEventListener
import com.intive.patronage.smarthome.feature.hvac.viewmodel.HvacViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val hvacDetailsModule = module {

    viewModel { (hvacViewEventListener: HVACViewEventListener, id: Int) ->
        HvacViewModel(
            get(),
            hvacViewEventListener,
            id
        )
    }
}