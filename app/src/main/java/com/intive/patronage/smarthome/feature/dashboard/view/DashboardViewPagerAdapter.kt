package com.intive.patronage.smarthome.feature.dashboard.view

import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class DashboardViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val mFragmentList = mutableListOf<Fragment>()
    private val mTitleList = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mTitleList.add(title)
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> mFragmentList[position]
            // add cases for other screens
            else -> {
                mFragmentList.last()
            }
        }
    }
}