package com.intive.patronage.smarthome.di

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeFragmentViewPagerAdapter
import org.koin.dsl.module

val viewPagerModule = module {
    factory { (fragmentManager: FragmentManager, lifecycle: Lifecycle) ->
        SmartHomeFragmentViewPagerAdapter( fragmentManager, lifecycle)
    }
}