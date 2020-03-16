package com.intive.patronage.smarthome.dashboard.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.model.DashboardSensor
import com.intive.patronage.smarthome.dashboard.viewmodel.DashboardViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import com.intive.patronage.smarthome.databinding.DashboardFragmentBinding
import org.koin.core.parameter.parametersOf

class DashboardFragment() : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModel()
    private val sensorsListAdapter: SensorsListAdapter by inject {
        parametersOf(::onItemClick)
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

    fun onItemClick(sensor: DashboardSensor){
        // coordinator TODO
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