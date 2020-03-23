package com.intive.patronage.smarthome.feature.dashboard.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class DashboardViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DashboardFragment()
            // add cases for other screens
            else -> {
                DashboardFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            // add to strings.xml
            0 -> "DashboardFragment()"
            else -> {
                "DashboardFragment()"
            }
        }
    }
}