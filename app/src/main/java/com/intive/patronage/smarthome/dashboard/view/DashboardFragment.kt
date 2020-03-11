package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.viewmodel.DashboardViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import com.intive.patronage.smarthome.databinding.DashboardFragmentBinding

class DashboardFragment() : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModel()
    private val sensorsListAdapter: SensorsListAdapter by inject()

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

    private fun setupRecyclerView(binding: DashboardFragmentBinding) {
        val recyclerView: RecyclerView = binding.sensorRecyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = sensorsListAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.dashboard_appbar)
    }
}