package com.intive.patronage.smarthome.feature.dashboard.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.intive.patronage.smarthome.R


class SmartHomeFragmentViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {



    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DashboardFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            // add to strings.xml
            0 -> "Dashboard"
            else -> "Home"
        }
    }
}