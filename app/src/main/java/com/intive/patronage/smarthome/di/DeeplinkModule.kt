package com.intive.patronage.smarthome.di

import android.content.Intent
import com.intive.patronage.smarthome.common.DeeplinkService
import com.intive.patronage.smarthome.navigator.DeeplinkCoordinator
import org.koin.dsl.module

val deeplinkModule = module {
    factory { (deeplinkCoordinator: DeeplinkCoordinator) ->
        DeeplinkService(
            deeplinkCoordinator
        )
    }
}