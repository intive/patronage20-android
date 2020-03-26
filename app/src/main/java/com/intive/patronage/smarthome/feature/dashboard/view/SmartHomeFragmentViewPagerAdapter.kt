package com.intive.patronage.smarthome.feature.dashboard.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.intive.patronage.smarthome.feature.home.view.HomeFragment


class SmartHomeFragmentViewPagerAdapter(fragmentManager: FragmentManager,
                                        private val titleMap: Map<Int, String>) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {



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
            0 -> titleMap[0]
            else -> titleMap[1]
        }
    }
}