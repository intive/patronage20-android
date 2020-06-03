package com.intive.patronage.smarthome.di

import com.intive.patronage.smarthome.feature.dashboard.model.api.service.NetworkConnectionService
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.dashboard.viewmodel.SmartHomeActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val smartHomeActivityModule = module {
    factory { (activity: SmartHomeActivity) -> NetworkConnectionService(activity) }

    viewModel { (networkConnection: NetworkConnectionService) ->
        SmartHomeActivityViewModel(get(), networkConnection) }
}
