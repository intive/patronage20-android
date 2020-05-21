package com.intive.patronage.smarthome.feature.dashboard.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.intive.patronage.smarthome.AnalyticsWrapper
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.SmartHomeErrorSnackbar
import com.intive.patronage.smarthome.feature.dashboard.viewmodel.SmartHomeFragmentViewModel
import com.intive.patronage.smarthome.navigator.POSITION_HOME_ON_VIEW_PAGER_KEY
import kotlinx.android.synthetic.main.smart_home_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SmartHomeFragment : Fragment() {

    private val mFirebaseAnalytics: AnalyticsWrapper by inject()
    private val viewPagerAdapter: SmartHomeFragmentViewPagerAdapter
            by inject { parametersOf(childFragmentManager, lifecycle) }
    private val smartHomeFragmentViewModel: SmartHomeFragmentViewModel by viewModel()
    private val alertSnackbar: SmartHomeErrorSnackbar by inject { parametersOf(activity) }


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
        switchViewPagerOnHomeAfterRedirectionDeeplink()
        observeViewModel()
    }

    private fun observeViewModel() {
        smartHomeFragmentViewModel.error.observe(this, Observer { error ->
            if (error && !alertSnackbar.snackbar.isShown)
                alertSnackbar.showSnackbar(getString(R.string.api_connection_error))
            else if (!error && alertSnackbar.snackbar.isShown)
                alertSnackbar.hideSnackbar()
        })
    }

    private fun setupTabLayout() {
        smartHomeViewPager.adapter = viewPagerAdapter
        TabLayoutMediator(smartHomeTabLayout, smartHomeViewPager) { tab, position ->
            setTabTextBasedOnPosition(tab, position)
        }.attach()

        smartHomeTabLayout.addOnTabSelectedListener(SmartHomeOnTabSelectedListener())
    }

    private fun setTabTextBasedOnPosition(tab: TabLayout.Tab, position: Int) {
        if (position == 0)
            tab.text = getString(R.string.dashboard_appbar)
        else
            tab.text = getString(R.string.home_appbar)
    }

    private inner class SmartHomeOnTabSelectedListener : OnTabSelectedListenerImpl() {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab?.position == 0) {
                mFirebaseAnalytics.switchScreenEvent(
                    activity,
                    getString(R.string.dahboard_fragment_class_name)
                )
            } else {
                mFirebaseAnalytics.switchScreenEvent(
                    activity,
                    getString(R.string.home_fragment_class_name)
                )
            }
        }
    }

    private fun setupToolbar(): ActionBar {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = ""
        toolbar.setDisplayHomeAsUpEnabled(false)
        context?.let {
            toolbar.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        it,
                        R.color.backgroundColor
                    )
                )
            )
        }
        (activity as SmartHomeActivity).showLogo()
        return toolbar
    }

    fun switchViewPagerOnHomeAfterRedirectionDeeplink() {
        val bundle = activity?.intent?.extras
        if (bundle != null && bundle.containsKey(POSITION_HOME_ON_VIEW_PAGER_KEY)) {
            val position = bundle.getInt(POSITION_HOME_ON_VIEW_PAGER_KEY)
            smartHomeViewPager.currentItem = position
        }
    }
}
