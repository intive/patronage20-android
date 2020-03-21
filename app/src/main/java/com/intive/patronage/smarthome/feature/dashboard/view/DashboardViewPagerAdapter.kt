package com.intive.patronage.smarthome.feature.dashboard.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class DashboardViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    

    override fun getItem(position: Int): Fragment {
        return DashboardFragment()
    }

    override fun getCount(): Int {
        return 1
    }
}