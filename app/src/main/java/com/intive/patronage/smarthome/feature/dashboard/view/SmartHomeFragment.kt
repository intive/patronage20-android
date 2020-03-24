package com.intive.patronage.smarthome.feature.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.smart_home_fragment.*

class SmartHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.smart_home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
    }

    fun setupTabLayout() {
        val pagerAdapter = SmartHomeFragmentViewPagerAdapter(childFragmentManager)
        smartHomeViewPager.adapter = pagerAdapter
        smartHomeTabLayout.setupWithViewPager(smartHomeViewPager)

        val toolbar = setupToolbar()
        smartHomeTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position!!) {
                    0 -> toolbar.title = resources.getString(R.string.dashboard_appbar)
                    1 -> toolbar.title = resources.getString(R.string.home_appbar)
                }

            }
        })
    }

    fun setupToolbar(): ActionBar {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.dashboard_appbar)
        toolbar.setDisplayHomeAsUpEnabled(false)
        return toolbar
    }

}