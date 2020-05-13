package com.intive.patronage.smarthome.feature.dashboard.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.intive.patronage.smarthome.AnalyticsWrapper
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.dashboard.viewmodel.SmartHomeFragmentViewModel
import kotlinx.android.synthetic.main.smart_home_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SmartHomeFragment : Fragment() {

    private val mFirebaseAnalytics: AnalyticsWrapper by inject()
    private val viewPagerAdapter: SmartHomeFragmentViewPagerAdapter
            by inject { parametersOf(childFragmentManager, lifecycle) }
    private val smartHomeFragmentViewModel: SmartHomeFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        smartHomeFragmentViewModel
        setupToolbar()
        return inflater.inflate(R.layout.smart_home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
    }

    fun setupTabLayout() {
        smartHomeViewPager.adapter = viewPagerAdapter
        TabLayoutMediator(smartHomeTabLayout, smartHomeViewPager)
        { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.dashboard_appbar)
                1 -> tab.text = getString(R.string.home_appbar)
            }
        }.attach()

        smartHomeTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position!!) {
                    0 -> mFirebaseAnalytics.switchScreenEvent(
                        activity, getString(R.string.dahboard_fragment_class_name)
                    )
                    1 -> mFirebaseAnalytics.switchScreenEvent(
                        activity, getString(R.string.home_fragment_class_name)
                    )
                }
            }
        })
    }

    fun setupToolbar(): ActionBar {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = ""
        toolbar.setDisplayHomeAsUpEnabled(false)
        toolbar.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.backgroundColor)))
        (activity as SmartHomeActivity).showLogo()
        return toolbar
    }
}
