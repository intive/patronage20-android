package com.intive.patronage.smarthome.feature.dashboard.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.intive.patronage.smarthome.R.layout
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.viewmodel.DashboardViewModel
import com.intive.patronage.smarthome.databinding.DashboardFragmentBinding
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.smart_home_activity.*


class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModel()
    private val sensorsListAdapter: SensorsListAdapter by inject {
        parametersOf(::onItemClick)
    }
    private val dashboardCoordinator: DashboardCoordinator by inject {
        parametersOf(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DashboardFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false)
        binding.lifecycleOwner = this
        binding.dashboardViewModelDataBind = dashboardViewModel
        setupRecyclerView(binding)


        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.dashboard_appbar)
        toolbar.setDisplayHomeAsUpEnabled(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val tabLayout = view.findViewById(R.id.tab_layout)
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
//        }.attach()
//        val pagerAdapter = DashboardViewPagerAdapter(childFragmentManager, lifecycle)
//        val viewPager = findViewById<ViewPager2>(R.id.dashboardViewPager)
//        dashboardViewPager.adapter = pagerAdapter
        TabLayoutMediator(dashboardTabLayout, dashboardViewPager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }

    private fun onItemClick(sensor: DashboardSensor){
        if (sensor.type == "RGBLight") {
            val bundle = Bundle()
            bundle.putInt("ID", sensor.id.toInt())
            dashboardCoordinator.goToLightsDetailsScreen(bundle)
        }
    }

    private fun setupRecyclerView(binding: DashboardFragmentBinding) {
        val recyclerView: RecyclerView = binding.sensorRecyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                layoutManager = LinearLayoutManager(activity)
            } else {
                layoutManager = GridLayoutManager(activity, 2)
            }
            adapter = sensorsListAdapter
        }
    }

}