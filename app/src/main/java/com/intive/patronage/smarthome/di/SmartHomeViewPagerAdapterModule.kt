package com.intive.patronage.smarthome.di

import androidx.fragment.app.FragmentManager
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeFragmentViewPagerAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val viewPagerModule = module {
    factory { (fragmentManager: FragmentManager) ->
        SmartHomeFragmentViewPagerAdapter(
            fragmentManager, mapOf(
                0 to androidContext().applicationContext.getString(R.string.dashboard_appbar),
                1 to androidContext().applicationContext.getString(R.string.home_appbar)
            )
        )
    }
}