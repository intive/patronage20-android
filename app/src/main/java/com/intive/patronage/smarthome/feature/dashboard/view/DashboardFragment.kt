package com.intive.patronage.smarthome.feature.dashboard.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.AnalyticsWrapper
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.DashboardFragmentBinding
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.TRANSFORMER_SEPARATOR
import com.intive.patronage.smarthome.feature.dashboard.viewmodel.DashboardViewModel
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DashboardFragment : Fragment() {
    private val analytics: AnalyticsWrapper by inject()
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

        return binding.root
    }

    private fun onItemClick(sensor: DashboardSensor) {
        val bundle = Bundle()
        bundle.putInt("ID", sensor.id.toInt())

        when (sensor.type) {
            "RGBLight" -> {
                dashboardCoordinator.goToLightsDetailsScreen(bundle)
                analytics.ledColorEvent(sensor.details.toInt())
            }
            "HVACRoom" -> {
                dashboardCoordinator.goToHvacDetalisScreen(bundle)
                analytics.hvacEvent(sensor.details.split(TRANSFORMER_SEPARATOR))
            }
            "windowBlind" -> {
                dashboardCoordinator.goToBlindDetailsScreen(bundle)
                analytics.blindLevelEvent(sensor.details.toInt())
            }
            "temperatureSensor" -> {
                dashboardCoordinator.goToTemperatureDetailsScreen(bundle)
            }
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