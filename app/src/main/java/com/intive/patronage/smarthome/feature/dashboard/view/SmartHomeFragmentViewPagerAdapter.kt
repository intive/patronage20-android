package com.intive.patronage.smarthome.feature.dashboard.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.intive.patronage.smarthome.feature.home.view.HomeFragment


class SmartHomeFragmentViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DashboardFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount() = 2

//    override fun getItem(position: Int): Fragment {
//        return when (position) {
//            0 -> DashboardFragment()
//            else -> HomeFragment()
//        }
//    }
//
//    override fun getCount(): Int = 2
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return when (position) {
//            // add to strings.xml
//            0 -> titleMap[0]
//            else -> titleMap[1]
//        }
//    }
}